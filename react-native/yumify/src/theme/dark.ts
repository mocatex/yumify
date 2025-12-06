import { DarkTheme as NavDark } from "@react-navigation/native";

export const DarkTheme = {
  ...NavDark,
  colors: {
    ...NavDark.colors,
    background: "#121212",
    card: "#1E1E1E",
    text: "#FFFFFF",
    border: "#272727",
    primary: "#D7C400",
    textSecondary: "#BBBBBB"
  }
};
