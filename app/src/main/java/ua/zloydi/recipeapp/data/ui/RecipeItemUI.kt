package ua.zloydi.recipeapp.data.ui

import ua.zloydi.recipeapp.data.ui.filterType.FilterTypeUI

data class RecipeItemUI(
    val title: String? = null,
    val image: String? = null,
    val time: Float? = null,
    val types: List<FilterTypeUI>
)
