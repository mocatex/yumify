import { fetchJson } from "./util.js";

const API_BASE_URL = "https://www.themealdb.com/api/json/v1/1/";

document.addEventListener("DOMContentLoaded", async () => {
  const mealId = getMealIdFromUrl();
  const container = document.querySelector("main");

  if (!mealId) {
    container.innerHTML = "<p>No meal ID provided.</p>";
    return;
  }

  container.innerHTML = "<p>Loading...</p>";

  try {
    const meal = await loadMealDetails(mealId);
    renderMealDetails(meal, container);
  } catch (err) {
    container.innerHTML = "<p>Error loading meal.</p>";
    console.error(err);
  }
});

function getMealIdFromUrl() {
  const params = new URLSearchParams(window.location.search);
  return params.get("id");
}

async function loadMealDetails(id) {
  const url = `${API_BASE_URL}lookup.php?i=${id}`;
  const json = await fetchJson(url);
  return json.meals?.[0] ?? null;
}

function extractIngredients(meal) {
  const list = [];
  for (let i = 1; i <= 20; i++) {
    const ing = meal[`strIngredient${i}`];
    const measure = meal[`strMeasure${i}`];

    if (ing && ing.trim() !== "") {
      list.push({ ingredient: ing.trim(), measure: (measure || "").trim() });
    }
  }
  return list;
}

function renderMealDetails(meal, container) {
  if (!meal) {
    container.innerHTML = "<p>Meal not found.</p>";
    return;
  }

  const ingredients = extractIngredients(meal);

  container.innerHTML = `
        <h1 style="color: var(--primary-color); text-align:center;">${
          meal.strMeal
        }</h1>

        <img src="${meal.strMealThumb}"
             alt="${meal.strMeal}"
             style="width:100%; max-width:500px; border-radius:1em; margin:0 auto; display:block;">

        <p style="text-align:center; margin-top:0.5em;">
            <strong>${meal.strCategory || ""}</strong> • ${meal.strArea || ""}
        </p>

        <hr style="width:100%;">

        <h2 style="margin-top:1.5em;">Ingredients</h2>
        <ul id="ingredient-list"></ul>

        <hr style="width:100%;">

        <h2 style="margin-top:1.5em;">Instructions</h2>
        <p style="white-space:pre-line;">${meal.strInstructions}</p>
    `;

  const ingList = document.getElementById("ingredient-list");
  ingredients.forEach(({ ingredient, measure }) => {
    const li = document.createElement("li");
    li.textContent = `${ingredient} (${measure || "n/a"})`;
    ingList.appendChild(li);
  });
}

export {};
