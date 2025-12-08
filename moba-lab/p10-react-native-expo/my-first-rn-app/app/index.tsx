import {Text, View, StyleSheet, ScrollView} from "react-native";
import {Image} from "expo-image";

// import JSON file with static data
import data from "@/assets/static_detail.json";

// parse all ingredients into a list
const ingredients: string[] = [];
for (let i = 1; i <= 20; i++) {
    const ingredient = (data as any)[`strIngredient${i}`];
    if (ingredient) ingredients.push(ingredient);
}

export default function Index() {
    return (
        <ScrollView contentContainerStyle={styles.container} style={styles.screen} >
            <Text style={styles.detail_title} >{data.strMeal}</Text >
            <Image source={require("@/assets/images/static_detail_photo.jpg")} style={styles.detail_image} />
            <Text style={{fontWeight: "bold", marginTop: 8}} >{data.strCategory} - {data.strArea}</Text >

            <View style={styles.separator} ></View >

            {/* Ingredients list */}
            <Text style={{fontWeight: "bold"}} >Ingredients:</Text >
            <View style={styles.ingredientsList} >
                {ingredients.map((ingredient, index) => (
                    <Text key={index} style={styles.ingredientItem} >
                        {"\u2022"} {ingredient}
                    </Text >
                ))}
            </View >

            <View style={styles.separator} ></View >

            {/* Instructions */}
            <Text style={{fontWeight: "bold", marginBottom: 8}} >Instructions:</Text >
            <Text style={styles.instructions}>{data.strInstructions}</Text >
        </ScrollView >
    );
}

const styles = StyleSheet.create({
    screen: {
        flex: 1,
        backgroundColor: "#fff",
    },
    container: {
        alignItems: "center",
        paddingBottom: 32
    },
    detail_image: {
        width: 320,
        aspectRatio: 1,
        borderRadius: 16
    },
    detail_title: {
        fontSize: 24,
        fontWeight: "bold",
        marginTop: 16,
        marginBottom: 16,
    },
    separator: {
        height: 2,
        width: "80%",
        backgroundColor: "#999999",
        marginVertical: 8,
    },
    ingredientsList: {
        alignSelf: "center",
        marginVertical: 8,
    },
    ingredientItem: {
        marginVertical: 2,
        alignSelf: "flex-start"
    },
    instructions: {
        paddingHorizontal: 32
    }
})
