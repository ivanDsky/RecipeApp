package ua.zloydi.recipeapp.data.repository

import ua.zloydi.recipeapp.R
import ua.zloydi.recipeapp.models.Category
import ua.zloydi.recipeapp.models.filterTypes.Dish

private val drawables = intArrayOf(
    R.drawable.alcohol_cocktail,
    R.drawable.biscuits_and_cookies,
    R.drawable.bread,
    R.drawable.cereals,
    R.drawable.condiments_and_sauces,
    R.drawable.dessert,
    R.drawable.drinks,
    R.drawable.main_course,
    R.drawable.omelet,
    R.drawable.pancake,
    R.drawable.preps,
    R.drawable.preserve,
    R.drawable.salad,
    R.drawable.sandwiches,
    R.drawable.soup,
    R.drawable.special_occasions,
    R.drawable.starter,
)
class CategoryRepository {
    fun getCategories(): List<Category> {
        return Dish.values.mapIndexed { index, dish -> Category(dish, drawables[index]) }
    }
}

object CategoryProvider{
    val repository = CategoryRepository()
}