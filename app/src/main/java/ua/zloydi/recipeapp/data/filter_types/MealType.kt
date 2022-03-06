package ua.zloydi.recipeapp.data.filter_types

enum class MealType {
    Breakfast,
    Lunch,
    Dinner,
    Snack,
    Teatime,
}

val mealMapper by lazy { MealMapper() }

class MealMapper : TypeMapper<MealType>(){
    override fun allName() = MealType.values()
    override val customNames: Array<Pair<MealType, String>>
        get() = emptyArray()
}