const API = "https://www.themealdb.com/api/json/v1/1/";

export interface Meal {
  idMeal: string;
  strMeal: string;
  strMealThumb: string;
  strCategory: string;
  strArea: string;
}

export async function searchMeals(query: string): Promise<Meal[]> {
  const json = await fetch(API + "search.php?s=" + query).then(r => r.json());
  return json.meals || [];
}

export async function randomMeal(): Promise<Meal[]> {
  const json = await fetch(API + "random.php").then(r => r.json());
  return json.meals || [];
}
