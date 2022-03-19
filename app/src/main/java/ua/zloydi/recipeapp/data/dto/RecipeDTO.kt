package ua.zloydi.recipeapp.data.dto

data class RecipeDTO(
    val uri: String? = null,
    val label: String? = null,
    val image: String? = null,
    val source: String? = null,
    val url: String? = null,
    val ingredients: Array<IngredientDTO>? = null,
    val calories: Float? = null,
    val totalTime: Float? = null,
    val totalWeight: Float? = null,
    val cuisineType: Array<String>? = null,
    val mealType: Array<String>? = null,
    val dishType: Array<String>? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RecipeDTO

        if (uri != other.uri) return false
        if (label != other.label) return false
        if (image != other.image) return false
        if (source != other.source) return false
        if (url != other.url) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uri?.hashCode() ?: 0
        result = 31 * result + (label?.hashCode() ?: 0)
        result = 31 * result + (image?.hashCode() ?: 0)
        result = 31 * result + (source?.hashCode() ?: 0)
        result = 31 * result + (url?.hashCode() ?: 0)
        return result
    }

}