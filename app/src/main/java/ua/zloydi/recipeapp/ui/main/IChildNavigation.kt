package ua.zloydi.recipeapp.ui.main

import ua.zloydi.recipeapp.data.retrofit.RecipeQuery
import ua.zloydi.recipeapp.models.dto.recipes.RecipeItemDTO
import ua.zloydi.recipeapp.models.filterTypes.SearchFilter

interface IChildNavigation {
    fun openDetail(item: RecipeItemDTO)
    fun openCategory(searchCategory: RecipeQuery.Category)
}

interface IParentNavigation {
    fun openParent()
    fun openSearch(searchFilter: SearchFilter)
}