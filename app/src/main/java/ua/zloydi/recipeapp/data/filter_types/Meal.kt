package ua.zloydi.recipeapp.data.filter_types

enum class Meal(override val label: String) : FilterType{
    Breakfast("Breakfast"),
    Lunch("Lunch"),
    Dinner("Dinner"),
    Snack("Snack"),
    Teatime("Teatime"),
}

object MealMapper : FilterTypeMapper<Meal>(){
    override fun values() = Meal.values()
}