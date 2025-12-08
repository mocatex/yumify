import { View, TextInput, Button, FlatList, Text, Switch } from "react-native";
import { useState, useEffect } from "react";
import { searchMeals, randomMeal, Meal } from "@/src/api/meals";
import { MealCard } from "@/src/components/MealCard";
import { useRouter } from "expo-router";
import { useNavigation } from "@react-navigation/native";
import { useTheme } from "@/src/context/ThemeContext";
import { global as styles } from "../src/styles/global";

export default function IndexScreen() {
  const router = useRouter();
  const navigation = useNavigation();

  const { theme, themeName, toggleTheme } = useTheme();

  const [query, setQuery] = useState("");
  const [meals, setMeals] = useState<Meal[]>([]);
  const [loading, setLoading] = useState(false);

  async function handleSearch(random = false) {
    setLoading(true);
    const result = random ? await randomMeal() : await searchMeals(query);
    setMeals(result);
    setLoading(false);
  }

  useEffect(() => {
    navigation.setOptions({
      headerRight: () => (
        <Switch
          value={themeName === "dark"}
          onValueChange={toggleTheme}
          thumbColor={themeName === "dark" ? "#D7C400" : "#888"}
          trackColor={{ true: "#444", false: "#CCC" }}
        />
      )
    });
  }, [themeName]);

  return (
    <View style={[styles.container, { backgroundColor: theme.colors.background }]}>
      <TextInput
        placeholder="Search recipes..."
        placeholderTextColor={theme.colors.text + "88"}
        style={[
          styles.input,
          {
            backgroundColor: theme.colors.card,
            borderColor: theme.colors.border,
            color: theme.colors.text
          }
        ]}
        value={query}
        onChangeText={setQuery}
      />

      <View style={styles.row}>
        <Button title="Search" color={theme.colors.primary} onPress={() => handleSearch(false)} />
        <Button title="Random" color={theme.colors.primary} onPress={() => handleSearch(true)} />
      </View>

      {loading && <Text style={{ color: theme.colors.text }}>Loading...</Text>}

      <FlatList
        data={meals}
        keyExtractor={(m) => m.idMeal}
        renderItem={({ item }) => (
          <MealCard
            meal={item}
            onPress={() => router.push(`/detail/${item.idMeal}`)}
          />
        )}
      />
    </View>
  );
}