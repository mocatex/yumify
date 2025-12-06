import { StyleSheet } from "react-native";

export const global = StyleSheet.create({
  container: {
    padding: 20,
    flex: 1,
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
  }
});