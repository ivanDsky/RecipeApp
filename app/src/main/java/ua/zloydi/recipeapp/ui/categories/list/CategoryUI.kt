package ua.zloydi.recipeapp.ui.categories.list

import androidx.annotation.DrawableRes

data class CategoryUI(
    val name: String,
    @DrawableRes val icon: Int,
    val onClick: () -> Unit
)