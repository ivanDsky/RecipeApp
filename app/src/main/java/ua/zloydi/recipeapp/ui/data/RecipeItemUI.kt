package ua.zloydi.recipeapp.ui.data

import ua.zloydi.recipeapp.ui.data.filterType.FilterTypeUI

data class RecipeItemUI(
    val id: String? = null,
    val title: String? = null,
    val image: String? = null,
    val time: Float? = null,
    val types: List<FilterTypeUI>,
    val onClick: () -> Unit
) : java.io.Serializable
