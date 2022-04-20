package ua.zloydi.recipeapp.models.dto

data class IngredientDTO(
    val foodId: String,
    val food: String?= null,
    val foodCategory: String?= null,
    val text: String? = null,
    val image: String? = null,
    val measure: String? = null,
    val weight: Float? = null,
    val quantity: Float? = null,
)