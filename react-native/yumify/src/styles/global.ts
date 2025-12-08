import {StyleSheet} from "react-native";

export const global = StyleSheet.create({
    container: {
        padding: 20,
        flex: 1,
    },
    details_container: {
        alignItems: "center",
        paddingBottom: 32
    },
    row: {
        flexDirection: "row",
        justifyContent: "space-between",
        marginBottom: 20,
    },
    title: {
        fontSize: 22,
        fontWeight: "bold",
        marginBottom: 8,
        marginTop: 16,
    },
    card: {
        flexDirection: "row",
        padding: 12,
        marginBottom: 12,
        borderWidth: 1,
        borderRadius: 14,
        alignItems: "center"
    },
    img: {
        width: 85,
        height: 85,
        borderRadius: 10
    },
    input: {
        borderWidth: 1,
        padding: 12,
        borderRadius: 8,
        marginBottom: 12
    },
    info: {
        marginLeft: 14,
        flexShrink: 1
    },
    cardTitle: {
        fontWeight: "bold",
        fontSize: 17,
        marginBottom: 4
    },
    separator: {
        height: 2,
        width: "80%",
        backgroundColor: "#555",
        marginVertical: 8,
    },
    detail_image: {
        width: 320,
        aspectRatio: 1,
        borderRadius: 16
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
});