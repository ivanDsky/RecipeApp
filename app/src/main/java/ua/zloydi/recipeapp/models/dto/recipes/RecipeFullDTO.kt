package ua.zloydi.recipeapp.models.dto.recipes

import ua.zloydi.recipeapp.models.dto.IngredientDTO

data class RecipeFullDTO(
    val uri: String = "",
    val label: String? = null,
    val image: String? = null,
    val totalTime: Float? = null,
    val source: String? = null,
    val url: String? = null,
    val dishType: List<String>,
    val mealType: List<String>,
    val cuisineType: List<String>,
    val ingredients: List<IngredientDTO>
){
    constructor(itemDTO: RecipeItemDTO, detailDTO: RecipeDetailDTO) : this(
        itemDTO.uri,
        itemDTO.label,
        itemDTO.image,
        itemDTO.totalTime,
        detailDTO.source,
        detailDTO.url,
        detailDTO.dishType,
        detailDTO.mealType,
        detailDTO.cuisineType,
        detailDTO.ingredients
    )

    val id
        get() = uri.substringAfterLast("#recipe_")

    val itemDTO
        get() = RecipeItemDTO(uri,label, image, totalTime)

    val detailDTO
        get() = RecipeDetailDTO(uri, source, url, dishType, mealType, cuisineType, ingredients)
}