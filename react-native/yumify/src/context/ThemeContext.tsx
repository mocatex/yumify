import React, { createContext, useState, useContext } from "react";
import { DarkTheme } from "../theme/dark";
import { LightTheme } from "../theme/light";

type ThemeName = "light" | "dark";

type ThemeContextType = {
  theme: any;
  themeName: ThemeName;
  toggleTheme: () => void;
};

const ThemeContext = createContext<ThemeContextType | null>(null);

export function ThemeProvider({ children }: { children: React.ReactNode }) {
  const [themeName, setThemeName] = useState<ThemeName>("dark");

  const toggleTheme = () => {
    setThemeName((prev) => (prev === "dark" ? "light" : "dark"));
  };

  const theme = themeName === "dark" ? DarkTheme : LightTheme;

  return (
    <ThemeContext.Provider value={{ theme, themeName, toggleTheme }}>
      {children}
    </ThemeContext.Provider>
  );
}

export function useTheme() {
  const ctx = useContext(ThemeContext);
  if (!ctx) throw new Error("useTheme must be used inside ThemeProvider");
  return ctx;
}