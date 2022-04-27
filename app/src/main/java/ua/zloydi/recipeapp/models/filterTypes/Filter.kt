package ua.zloydi.recipeapp.models.filterTypes

data class Filter(
    val categories: List<Dish> = emptyList(),
    val meals: List<Meal> = emptyList(),
    val cuisines: List<Cuisine> = emptyList(),
) : java.io.Serializable{
    fun isEmpty() = categories.isEmpty() && meals.isEmpty() && cuisines.isEmpty()
}
