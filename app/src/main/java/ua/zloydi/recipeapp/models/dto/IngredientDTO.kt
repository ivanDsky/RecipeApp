package ua.zloydi.recipeapp.models.dto

data class IngredientDTO(
    val foodId: String,
    val food: String?= null,
    val foodCategory: String?= null,
    val text: String? = null,
    val image: String? = null,
    val measure: String? = null,
    val weight: Float? = null,
    val quantity: Float? = null,
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is IngredientDTO) return false

        if (food != other.food) return false
        if (image != other.image) return false

        return true
    }

    override fun hashCode(): Int {
        var result = food?.hashCode() ?: 0
        result = 31 * result + (image?.hashCode() ?: 0)
        return result
    }


}

fun List<IngredientDTO>.unique(): List<IngredientDTO>{
    val unique = toHashSet()
    return filter { unique.remove(it) }
}