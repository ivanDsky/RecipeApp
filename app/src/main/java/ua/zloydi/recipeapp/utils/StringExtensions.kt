package ua.zloydi.recipeapp.utils

fun String.firstCaps(): String = buildString {
    if(this@firstCaps.isNotEmpty())
        append(this@firstCaps[0].uppercaseChar())
    if(this@firstCaps.length >= 2)
        append(this@firstCaps.substring(1))
}