package ua.zloydi.recipeapp.models

import androidx.annotation.DrawableRes
import ua.zloydi.recipeapp.models.filter_types.Dish

data class Category(
    val dish: Dish,
    @DrawableRes val drawable: Int,
){
    val name = dish.label
}