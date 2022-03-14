package ua.zloydi.recipeapp.data.ui

import java.io.Serializable

data class IngredientUI(
    val food: String? = null,
    val text: String? = null,
    val measure: String? = null
) : Serializable
