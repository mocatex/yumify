package ch.moritz.yumify_android.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(val meals: List<MealDto>?)

@Serializable
data class MealDto(
    @SerialName("idMeal") val id: String,
    @SerialName("strMeal") val title: String? = null,
    @SerialName("strMealThumb") val thumb: String? = null
)

// Domain model that your UI uses
data class Meal(val id: String, val title: String, val thumb: String?)

fun MealDto.toDomain() = Meal(
    id = id,
    title = title.orEmpty(),
    thumb = thumb
)
