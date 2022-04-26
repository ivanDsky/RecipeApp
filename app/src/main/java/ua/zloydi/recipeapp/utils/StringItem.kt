package ua.zloydi.recipeapp.utils

import androidx.annotation.StringRes

sealed class StringItem{
    class Res(@StringRes val id: Int) : StringItem()
    class String(val text: kotlin.String) : StringItem()
}
