import { DefaultTheme as NavLight } from "@react-navigation/native";

export const LightTheme = {
  ...NavLight,
  colors: {
    ...NavLight.colors,
    background: "#FFFFFF",
    card: "#F5F5F5",
    text: "#111111",
    border: "#DDDDDD",
    primary: "#D7C400",
    textSecondary: "#555555"
  }
};
