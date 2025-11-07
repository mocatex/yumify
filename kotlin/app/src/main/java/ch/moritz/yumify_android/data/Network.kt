package ch.moritz.yumify_android.data

import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL
import java.net.URLEncoder

suspend fun fetchMeals(query: String): List<Meal> {
    val base = "https://www.themealdb.com/api/json/v1/1/"
    val url = base + "search.php?s=" + URLEncoder.encode(query, "UTF-8")

    val raw = withContext(Dispatchers.IO) {
        URL(url).openStream().bufferedReader().use { it.readText() }
    }

    val json = Json { ignoreUnknownKeys = true }
    val dto = json.decodeFromString<SearchResponse>(raw)
    return dto.meals?.map { it.toDomain() } ?: emptyList()
}

suspend fun fetchRandomMeal(): List<Meal> {
    val base = "https://www.themealdb.com/api/json/v1/1/"
    val url = base + "random.php"

    val raw = withContext(Dispatchers.IO) {
        URL(url).openStream().bufferedReader().use { it.readText() }
    }

    val json = Json { ignoreUnknownKeys = true }
    val dto = json.decodeFromString<SearchResponse>(raw)
    return dto.meals?.map { it.toDomain() } ?: emptyList()
}
