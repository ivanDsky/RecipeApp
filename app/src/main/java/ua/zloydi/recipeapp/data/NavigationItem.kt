package ua.zloydi.recipeapp.data

import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

data class NavigationItem(val fragment: Fragment, @StringRes val title: Int, @IdRes val id: Int)