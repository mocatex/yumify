import { View, Text } from "react-native";
import { useLocalSearchParams } from "expo-router";
import { useTheme } from "../../src/context/ThemeContext";
import { global as styles } from "../../src/styles/global";

export default function DetailPage() {
  const params = useLocalSearchParams<{ id?: string }>();
  const id = params.id ?? "unknown";

  const { theme } = useTheme();

  return (
    <View
      style={[
        styles.container,
        { backgroundColor: theme.colors.background }
      ]}
    >
      <Text style={[styles.title, { color: theme.colors.text }]}>
        Meal ID: {id}
      </Text>

      <Text style={{ color: theme.colors.textSecondary }}>
        Details coming soon…
      </Text>
    </View>
  );
}
