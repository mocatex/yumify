import { View, Text, Image, TouchableOpacity } from "react-native";
import { Meal } from "../api/meals";
import { useTheme } from "../context/ThemeContext";
import { global as styles } from "../styles/global";

type Props = {
  meal: Meal;
  onPress: () => void;
};

export function MealCard({ meal, onPress }: Props) {
  const { theme } = useTheme();

  return (
    <TouchableOpacity
      style={[
        styles.card,
        {
          backgroundColor: theme.colors.card,
          borderColor: theme.colors.border
        }
      ]}
      onPress={onPress}
    >
      <Image source={{ uri: meal.strMealThumb }} style={styles.img} />

      <View style={styles.info}>
        <Text style={[styles.cardTitle, { color: theme.colors.text }]}>
          {meal.strMeal}
        </Text>

        <Text style={{ color: theme.colors.textSecondary }}>
          {meal.strCategory}
        </Text>

        <Text style={{ color: theme.colors.textSecondary }}>
          {meal.strArea}
        </Text>
      </View>
    </TouchableOpacity>
  );
}