package ua.zloydi.recipeapp.data.ui

data class RecipeUI(
    val title: String? = null,
    val image: String? = null,
    val ingredients: List<IngredientUI>,
    val types: List<FilterTypeUI>
)
