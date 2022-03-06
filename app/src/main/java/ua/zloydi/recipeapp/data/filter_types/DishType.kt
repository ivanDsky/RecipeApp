package ua.zloydi.recipeapp.data.filter_types

enum class DishType {
    AlcoholCocktail,
    BiscuitsAndCookies,
    Bread,
    Cereals,
    CondimentsAndSauces,
    Drinks,
    Desserts,
    Egg,
    MainCourse,
    Omelet,
    Pancake,
    Preps,
    Preserve,
    Salad,
    Sandwiches,
    Soup,
    Starter,
}

val dishMapper by lazy { DishMapper()}

class DishMapper : TypeMapper<DishType>() {
    override fun allName() = DishType.values()
    override val customNames: Array<Pair<DishType, String>>
        get() = arrayOf(
            DishType.AlcoholCocktail to "Alcohol-cocktail",
            DishType.BiscuitsAndCookies to "Biscuits and cookies",
            DishType.CondimentsAndSauces to "Condiments and sauces",
            DishType.MainCourse to "Main course",
        )
}