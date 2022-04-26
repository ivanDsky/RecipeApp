package ua.zloydi.recipeapp.ui.data

data class RecipeItemUI(
    val id: String? = null,
    val title: String? = null,
    val image: String? = null,
    val time: Float? = null,
    val onClick: () -> Unit
) : java.io.Serializable
