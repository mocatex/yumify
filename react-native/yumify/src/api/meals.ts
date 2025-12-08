const API = "https://www.themealdb.com/api/json/v1/1/";

export interface Meal {
  idMeal: string;
  strMeal: string;
  strMealThumb: string;
  strCategory: string;
  strArea: string;
}

export interface MealDetail {
    strMeal: string;
    strCategory: string;
    strArea: string;
    strInstructions: string;
    strMealThumb: string;
    strIngredient1: string;
    strIngredient2: string;
    strIngredient3: string;
    strIngredient4: string;
    strIngredient5: string;
    strIngredient6: string;
    strIngredient7: string;
    strIngredient8: string;
    strIngredient9: string;
    strIngredient10: string;
    strIngredient11: string;
    strIngredient12: string;
    strIngredient13: string;
    strIngredient14: string;
    strIngredient15: string;
    strIngredient16: string;
    strIngredient17: string;
    strIngredient18: string;
    strIngredient19: string;
    strIngredient20: string;
}

export async function searchMeals(query: string): Promise<Meal[]> {
  const json = await fetch(API + "search.php?s=" + query).then(r => r.json());
  return json.meals || [];
}

export async function randomMeal(): Promise<Meal[]> {
  const json = await fetch(API + "random.php").then(r => r.json());
  return json.meals || [];
}

export async function getMealDetails(id: string): Promise<MealDetail | null> {
    const json = await fetch(API + "lookup.php?i=" + id).then(r => r.json());
    return json.meals ? json.meals[0] : null;
}
