package ua.zloydi.recipeapp.models.dto.recipes

data class RecipeItemDTO(
    val uri: String = "",
    val label: String? = null,
    val image: String? = null,
    val totalTime: Float? = null,
) : java.io.Serializable{
    val id
        get() = uri.substringAfterLast("#recipe_")
}