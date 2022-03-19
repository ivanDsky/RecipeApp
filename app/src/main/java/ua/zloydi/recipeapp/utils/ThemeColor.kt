package ua.zloydi.recipeapp.utils

import android.content.Context
import android.content.res.ColorStateList
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt

fun Context.getThemeColor(@AttrRes color: Int) = TypedValue().run {
    theme.resolveAttribute(color, this, true)
    data
}
