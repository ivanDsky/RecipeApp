package ua.zloydi.recipeapp.data.repository

import ua.zloydi.recipeapp.R
import ua.zloydi.recipeapp.models.Category
import ua.zloydi.recipeapp.models.filter_types.Dish

class CategoryRepository {
    fun getCategories(): List<Category>{
        return Dish.values().map { Category(it, R.drawable.category) }
    }
}

object CategoryProvider{
    val repository = CategoryRepository()
}