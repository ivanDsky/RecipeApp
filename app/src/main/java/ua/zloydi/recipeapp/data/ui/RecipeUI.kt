package ua.zloydi.recipeapp.data.ui

import ua.zloydi.recipeapp.data.ui.filterType.FilterTypeUI

data class RecipeUI(
    val title: String? = null,
    val image: String? = null,
    val ingredients: List<IngredientUI>,
    val types: List<FilterTypeUI>
)
