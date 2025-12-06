import { Stack } from "expo-router";
import { ThemeProvider as NavThemeProvider } from "@react-navigation/native";
import { ThemeProvider, useTheme } from "../src/context/ThemeContext";

function RootNavigation() {
  const { theme } = useTheme();

  return (
    <NavThemeProvider value={theme}>
      <Stack>
        <Stack.Screen name="index" options={{ title: "Yumify Search" }} />
        <Stack.Screen name="detail/[id]" options={{ title: "Meal Details" }} />
      </Stack>
    </NavThemeProvider>
  );
}

export default function Layout() {
  return (
    <ThemeProvider>
      <RootNavigation />
    </ThemeProvider>
  );
}