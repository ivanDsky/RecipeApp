package ua.zloydi.recipeapp.ui.mappers

import ua.zloydi.recipeapp.models.dto.IngredientDTO
import ua.zloydi.recipeapp.models.dto.recipes.RecipeDetailDTO
import ua.zloydi.recipeapp.models.dto.recipes.RecipeItemDTO
import ua.zloydi.recipeapp.ui.data.IngredientUI
import ua.zloydi.recipeapp.ui.data.RecipeItemUI
import ua.zloydi.recipeapp.ui.data.RecipeUI

fun RecipeItemDTO.toUI(onClick: () -> Unit) = RecipeItemUI(
    id = id,
    title = label,
    image = image,
    time = totalTime,
    types = emptyList(),
    onClick = onClick
)

fun RecipeDetailDTO.toUI(item: RecipeItemDTO) = RecipeUI(
    title = item.label,
    image = item.image,
    source = source,
    description = null,
    url = url,
    ingredients = ingredients?.map { it.toUI() }?.toTypedArray(),
    calories = null,
    totalTime = item.totalTime,
    cuisineType = emptyArray(),
    dishType = emptyArray(),
    mealType = emptyArray()
)

fun IngredientDTO.toUI() = IngredientUI(
    food, text, measure, image
)

