import {getMeals} from "./util.js"

/**
 * @typedef {Object} Meal
 * @property {string} idMeal - Unique ID of the meal
 * @property {string} strMeal - Name of the meal
 * @property {string} strMealThumb - URL of the meal thumbnail image
 * @property {string} strCategory - Category of the meal
 * @property {string} strArea - Area of the meal
 */


document.addEventListener( 'DOMContentLoaded', () => {
    const searchInput = document.getElementById('search-input');
    const searchButton = document.getElementById('search-button');
    const randomButton = document.getElementById('random-recipe-button');
    const resultContainer = document.getElementById('results');

    async function handleSearch(searchRandom = true) {
        const query = searchInput.value.trim();
        if (!query) return;

        resultContainer.innerHTML = "Loading...";
        
        let items = [];

        try {
            items = await getMeals(query, searchRandom);
            console.log("Fetched meals:", items);
        } catch (error) {
            console.error("Error fetching meals:", error);
            resultContainer.innerHTML = "<p>Error fetching meals. Please try again later.</p>";
        }
        
        if (items.length === 0) {
            resultContainer.innerHTML = "<p>No meals found. Please try a different search term.</p>";
        }
        
        showResults(items);
        
    }

    searchButton.addEventListener('click', () => handleSearch(false));
    searchInput.addEventListener('keypress', (event) => {
        if (event.key === 'Enter') {
            handleSearch(false)
                .then(r => console.log(r));
        }
    });
    randomButton.addEventListener('click', () => handleSearch(true));
})


function showResults(items) {
    const resultContainer = document.getElementById('results');
    resultContainer.innerHTML = "";
    
    for(const item of items) {
        const article = document.createElement('article');
        article.className = "meal-card";
        article.tabIndex = 0;
        article.role = "button";
        article.onclick = () => showMealDetails(`${item.idMeal}`);

        article.innerHTML = `
            <header class="meal-card-header">
            <h2 class="meal-title">${item.strMeal}</h2>
            </header>
            
            <div class="meal-card-body">
            <img src="${item.strMealThumb}" alt="${item.strMeal}" class="meal-image">
            <div class="meal-tags">
            <span class="meal-tag">Category: ${item.strCategory}</span>
            <span class="meal-tag">Area: ${item.strArea}</span>
            <span class="meal-id">Meal ID: ${item.idMeal}</span>
            </div>
            </div>
        `;
        
        resultContainer.appendChild(article);
    }
}


function showMealDetails(mealId) {
    globalThis.location.href = `detail.html?id=${mealId}`;
}