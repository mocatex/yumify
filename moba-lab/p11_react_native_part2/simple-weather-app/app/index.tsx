import React, {useMemo, useState} from "react";
import {
    ActivityIndicator,
    Keyboard,
    Pressable,
    StyleSheet,
    Text,
    TextInput,
    View,
} from "react-native";

import {SafeAreaView} from "react-native-safe-area-context";

// ========= Geocoding API ==========
type GeoResult = {
    name: string;
    latitude: number;
    longitude: number;
    country?: string;
    admin1?: string;
    timezone?: string;
};

type GeoResponse = {
    results?: GeoResult[];
};

// ========= Weather API ==========
type WeatherCurrent = {
    time: string;
    temperature_2m: number;
    relative_humidity_2m?: number;
    apparent_temperature?: number;
    weather_code?: number;
    wind_speed_10m?: number;
};

type WeatherResponse = {
    current?: WeatherCurrent;
    current_units?: Partial<Record<keyof WeatherCurrent, string>>;
};
// ========= Weather code to text ==========
const wmoText = (code?: number) => {
    if (code == null) return "—";
    const map: Record<number, string> = {
        0: "Clear",
        1: "Mainly clear",
        2: "Partly cloudy",
        3: "Overcast",
        45: "Fog",
        48: "Depositing rime fog",
        51: "Light drizzle",
        53: "Drizzle",
        55: "Dense drizzle",
        61: "Slight rain",
        63: "Rain",
        65: "Heavy rain",
        71: "Slight snow",
        73: "Snow",
        75: "Heavy snow",
        80: "Rain showers",
        81: "Rain showers",
        82: "Violent rain showers",
        95: "Thunderstorm",
    };
    return map[code] ?? `Weather code ${code}`;
};

// ========= Fetch JSON ==========
async function fetchJson<T>(url: string): Promise<T> {
    const res = await fetch(url);
    if (!res.ok) throw new Error(`HTTP ${res.status} ${res.statusText}`);
    return (await res.json()) as T;
}

export default function Index() {
    const [query, setQuery] = useState("Zurich");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const [place, setPlace] = useState<GeoResult | null>(null);
    const [weather, setWeather] = useState<WeatherResponse | null>(null);

    const subtitle = useMemo(() => {
        if (!place) return "";
        const parts = [place.name, place.admin1, place.country].filter(Boolean);
        return parts.join(", ");
    }, [place]);

    const onSearch = async () => {
        const q = query.trim();
        if (q.length < 2) {
            setError("Please enter at least 2 characters.");
            return;
        }

        Keyboard.dismiss();
        setLoading(true);
        setError(null);

        try {
            // 1) Geocode (name -> lat/lon)
            const geoUrl =
                `https://geocoding-api.open-meteo.com/v1/search` +
                `?name=${encodeURIComponent(q)}&count=1&language=en&format=json`;

            const geo = await fetchJson<GeoResponse>(geoUrl);
            const first = geo.results?.[0];
            if (!first) throw new Error("No matching location found.");

            setPlace(first);

            // 2) Weather (lat/lon -> current)
            const wxUrl =
                `https://api.open-meteo.com/v1/forecast` +
                `?latitude=${first.latitude}` +
                `&longitude=${first.longitude}` +
                `&current=temperature_2m,relative_humidity_2m,apparent_temperature,weather_code,wind_speed_10m` +
                `&timezone=auto`;

            const wx = await fetchJson<WeatherResponse>(wxUrl);
            if (!wx.current) throw new Error("Weather data missing in response.");
            setWeather(wx);
        } catch (e: any) {
            setWeather(null);
            setPlace(null);
            setError(e?.message ?? "Something went wrong.");
        } finally {
            setLoading(false);
        }
    };

    const units = weather?.current_units ?? {};
    const cur = weather?.current;

    return (
        <SafeAreaView style={styles.safe} >
            <View style={styles.container} >
                <Text style={styles.title} >Simple Weather App</Text >
                <Text style={styles.muted} >Type a place, get current conditions.</Text >

                <View style={styles.row} >
                    <TextInput
                        value={query}
                        onChangeText={setQuery}
                        placeholder="e.g. Zurich, London, Tokyo"
                        placeholderTextColor="#7a7a7a"
                        autoCorrect={false}
                        autoCapitalize="words"
                        returnKeyType="search"
                        onSubmitEditing={onSearch}
                        style={styles.input}
                    />
                    <Pressable
                        onPress={onSearch}
                        disabled={loading}
                        style={({pressed}) => [
                            styles.button,
                            loading && styles.buttonDisabled,
                            pressed && !loading && styles.buttonPressed,
                        ]}
                    >
                        <Text style={styles.buttonText} >{loading ? "…" : "Go"}</Text >
                    </Pressable >
                </View >

                {loading && (
                    <View style={styles.card} >
                        <ActivityIndicator />
                        <Text style={styles.muted} >Loading…</Text >
                    </View >
                )}

                {error && (
                    <View style={[styles.card, styles.cardError]} >
                        <Text style={styles.errorText} >{error}</Text >
                    </View >
                )}

                {place && cur && !loading && (
                    <View style={styles.card} >
                        <Text style={styles.place} >{subtitle}</Text >
                        <Text style={styles.big} >
                            {cur.temperature_2m}
                            {units.temperature_2m ?? "°C"}
                        </Text >
                        <Text style={styles.muted} >{wmoText(cur.weather_code)}</Text >
                    </View >
                )}
            </View >
        </SafeAreaView >
    );
}

const styles = StyleSheet.create({
    safe: {flex: 1, backgroundColor: "#0B0B0D"},
    container: {flex: 1, padding: 18, gap: 12},
    title: {color: "white", fontSize: 28, fontWeight: "700"},
    muted: {color: "#B8B8B8"},

    row: {flexDirection: "row", gap: 10, alignItems: "center"},
    input: {
        flex: 1,
        backgroundColor: "#141418",
        borderColor: "#26262D",
        borderWidth: 1,
        borderRadius: 12,
        paddingHorizontal: 14,
        paddingVertical: 12,
        color: "white",
    },
    button: {
        backgroundColor: "#FFFFFF",
        borderRadius: 12,
        paddingHorizontal: 16,
        paddingVertical: 12,
    },
    buttonPressed: {transform: [{scale: 0.98}]},
    buttonDisabled: {opacity: 0.5},
    buttonText: {color: "#0B0B0D", fontWeight: "700"},

    card: {
        backgroundColor: "#111116",
        borderColor: "#26262D",
        borderWidth: 1,
        borderRadius: 16,
        padding: 16,
        gap: 6,
    },
    cardError: {borderColor: "#5A1F1F"},
    errorText: {color: "#FFB4B4"},

    place: {color: "white", fontSize: 16, fontWeight: "600"},
    big: {color: "white", fontSize: 44, fontWeight: "800"},
});
