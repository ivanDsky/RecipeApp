package ua.zloydi.recipeapp.data.repository

import ua.zloydi.recipeapp.ui.categories.CategoryUI

class CategoryRepository {
    fun getCategories(): List<CategoryUI>{
        return emptyList()
    }
}

object CategoryProvider{
    val repository = CategoryRepository()
}