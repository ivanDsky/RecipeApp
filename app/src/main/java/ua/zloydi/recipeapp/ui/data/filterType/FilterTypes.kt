package ua.zloydi.recipeapp.ui.data.filterType

data class CuisineUI(override val name: String, override val onClick: () -> Unit) : FilterTypeUI
data class MealUI(override val name: String, override val onClick: () -> Unit) : FilterTypeUI
data class DishUI(override val name: String, override val onClick: () -> Unit) : FilterTypeUI