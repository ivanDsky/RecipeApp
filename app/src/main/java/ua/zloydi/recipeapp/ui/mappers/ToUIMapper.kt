package ua.zloydi.recipeapp.ui.mappers

import ua.zloydi.recipeapp.models.dto.IngredientDTO
import ua.zloydi.recipeapp.models.dto.recipes.RecipeDetailDTO
import ua.zloydi.recipeapp.models.dto.recipes.RecipeItemDTO
import ua.zloydi.recipeapp.models.filter_types.*
import ua.zloydi.recipeapp.ui.data.IngredientUI
import ua.zloydi.recipeapp.ui.data.RecipeItemUI
import ua.zloydi.recipeapp.ui.data.RecipeUI
import ua.zloydi.recipeapp.ui.data.filterType.CuisineUI
import ua.zloydi.recipeapp.ui.data.filterType.DishUI
import ua.zloydi.recipeapp.ui.data.filterType.FilterTypeUI
import ua.zloydi.recipeapp.ui.data.filterType.MealUI

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
    ingredients = ingredients.map{it.toUI()},
    calories = null,
    totalTime = item.totalTime,
    dishType = item.dishType.toUI(Dish.mapper, ::DishUI),
    mealType = item.mealType.toUI(Meal.mapper,::MealUI),
    cuisineType = item.cuisineType.toUI(Cuisine.mapper, ::CuisineUI),
)

fun <T : FilterType, V : FilterTypeUI> List<String>.toUI(
    mapper: Mapper<T>, factory: (String) -> V
) = flatMap { it.split('/') }
    .map { factory(mapper[it].label) }

fun IngredientDTO.toUI() = IngredientUI(
    food, text, measure, image
)

