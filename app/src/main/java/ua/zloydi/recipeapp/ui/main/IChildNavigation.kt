package ua.zloydi.recipeapp.ui.main

import ua.zloydi.recipeapp.ui.data.RecipeItemUI

interface IChildNavigation {
    fun openDetail(item: RecipeItemUI)
}