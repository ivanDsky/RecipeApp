package ua.zloydi.recipeapp.data.filter_types

enum class MealType {
    Breakfast,
    Lunch,
    Dinner,
    Snack,
    Teatime,
}

fun MealType.getString() = toString()