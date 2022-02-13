package ua.zloydi.recipeapp.data

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment

data class NavigationItem(val fragment: Fragment, val title: String, @IdRes val id: Int)