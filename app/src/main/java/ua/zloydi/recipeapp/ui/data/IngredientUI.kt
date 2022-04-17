package ua.zloydi.recipeapp.ui.data

import java.io.Serializable

data class IngredientUI(
    val food: String? = null,
    val text: String? = null,
    val measure: String? = null,
    val image: String? = null
) : Serializable
