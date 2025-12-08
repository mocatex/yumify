import {View, Text, ScrollView, Image} from "react-native";
import {getMealDetails, MealDetail} from "@/src/api/meals";
import {useLocalSearchParams} from "expo-router";
import {useTheme} from "@/src/context/ThemeContext";
import {global as styles} from "../../src/styles/global";
import {useEffect, useState} from "react";

const BASE_URL = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=";

export default function DetailPage() {
    const params = useLocalSearchParams<{ id?: string }>();
    const id = params.id ?? "unknown";


    const [data, setData] = useState<MealDetail | null>(null);
    const [ingredients, setIngredients] = useState<string[]>([]);

    const {theme} = useTheme();

    useEffect(() => {
        getMealDetails(id).then((meal) => {
            setData(meal);

            if (!meal) {
                setIngredients([]);
                return;
            }

            const list: string[] = [];
            // create the ingredient list from data
            for (let i = 1; i <= 20; i++) {
                const key = `strIngredient${i}` as keyof MealDetail;
                const ingredient = meal[key];
                if (ingredient && ingredient.trim() !== "") {
                    list.push(ingredient);
                }
            }
            setIngredients(list);
        })
            .catch((error) => {
                setData(null);
                setIngredients([]);
                console.error(error);
            });

    }, [id]);

    if (!data) {
        return (
            <View style={styles.container} >
                <Text >Loading or meal not found...</Text >
            </View >
        );
    }

    return (
        <ScrollView contentContainerStyle={styles.details_container}
                    style={{flex: 1, backgroundColor: theme.colors.background}}
        >
            <Text style={[styles.title, {color: theme.colors.text}]} >{data.strMeal}</Text >
            <Image source={{uri: data.strMealThumb}} style={styles.detail_image} />
            <Text style={{fontWeight: "bold", marginTop: 8, color: theme.colors.text}} >{data.strCategory} - {data.strArea}</Text >

            <View style={styles.separator} ></View >

            {/* Ingredients list */}
            <Text style={{fontWeight: "bold", color: theme.colors.text}} >Ingredients:</Text >
            <View style={styles.ingredientsList} >
                {ingredients.map((ingredient, index) => (
                    <Text key={index} style={[styles.ingredientItem, {color: theme.colors.text}]} >
                        {"\u2022"} {ingredient}
                    </Text >
                ))}
            </View >

            <View style={styles.separator} ></View >

            {/* Instructions */}
            <Text style={{fontWeight: "bold", marginBottom: 8, color: theme.colors.text}} >Instructions:</Text >
            <Text style={[styles.instructions, {color: theme.colors.text}]} >{data.strInstructions}</Text >
        </ScrollView >
    );
}
