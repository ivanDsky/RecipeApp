package ua.zloydi.recipeapp.ui.core.adapter

abstract class RecipeItem {
    abstract val id: Int
    abstract fun isEquals(item: RecipeItem) : Boolean
}