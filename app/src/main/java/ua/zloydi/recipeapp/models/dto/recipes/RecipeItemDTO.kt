package ua.zloydi.recipeapp.models.dto.recipes

data class RecipeItemDTO(
    val uri: String? = null,
    val label: String? = null,
    val image: String? = null,
    val totalTime: Float? = null,
    val cuisineType: List<String>,
    val mealType: List<String>,
    val dishType: List<String>,
) : java.io.Serializable{
    val id
        get() = uri?.substringAfterLast("#recipe_")
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RecipeItemDTO

        if (label != other.label) return false
        if (uri != other.uri) return false
        if (image != other.image) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uri?.hashCode() ?: 0
        result = 31 * result + (label?.hashCode() ?: 0)
        result = 31 * result + (image?.hashCode() ?: 0)
        return result
    }

}