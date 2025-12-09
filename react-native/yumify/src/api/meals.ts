const API = "https://www.themealdb.com/api/json/v1/1/";

export interface Meal {
  idMeal: string;
  strMeal: string;
  strMealThumb: string;
  strCategory: string;
  strArea: string;
}

export interface MealDetail {
    idMeal: string;
    strMeal: string;
    strCategory: string;
    strArea: string;
    strInstructions: string;
    strMealThumb: string;
    ingredients: Array<{ name: string; measure: string }>;
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
    if (!json.meals) return null;
    
    const meal = json.meals[0];
    
    const ingredients = Object.keys(meal)
        .filter(key => key.startsWith('strIngredient') && meal[key]?.trim())
        .map((key, i) => ({
            name: meal[key].trim(),
            measure: meal[`strMeasure${i + 1}`]?.trim() || ''
        }));
    
    return {
        idMeal: meal.idMeal,
        strMeal: meal.strMeal,
        strCategory: meal.strCategory,
        strArea: meal.strArea,
        strInstructions: meal.strInstructions,
        strMealThumb: meal.strMealThumb,
        ingredients
    };
}