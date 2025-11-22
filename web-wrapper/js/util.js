// API call to meal-DB

const API_BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

/**
 * @typedef {Object} MealResponse
 * @property {Object[]} meals - Array of meals
 */


/**
 * Fetch JSON data from the given URL
 *
 * @param {string} url - URL to fetch
 * @param {object} options - Fetch options
 * @returns {Promise<any>} - Promise resolving to the JSON data
 */
async function fetchJson(url, options = {}) {
    const response = await fetch(url, {
        method: "GET",
        ...options
    });

    if (!response.ok) {
        const msg = `Error fetching data: ${response.status} ${response.statusText}`;
        throw new Error(msg);
    }

    return await response.json();
}

/**
 * Fetch meals based on the search query
 *
 * @param {string} query - Search query
 * @param {boolean} random - Whether to fetch a random meal or by search query
 * @returns {Promise<DataTransferItemList|*[]>} - Promise resolving to an array of meals
 */
async function getMeals(query, random = true) {

    const url = random
        ? `${API_BASE_URL}random.php`
        : `${API_BASE_URL}search.php?s=${query}`;

    try {
        const json = await fetchJson(url);
        return json.meals || [];
    } catch (error) {
        console.error("Error fetching meals:", error);
        throw error;
    }
}

export {getMeals, fetchJson};