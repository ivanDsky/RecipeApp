package ua.zloydi.recipeapp.data.filter_types

enum class DishType{
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

fun DishType.getString() = when(this){
    DishType.AlcoholCocktail -> "Alcohol-cocktail"
    DishType.BiscuitsAndCookies -> "Biscuits and cookies"
    DishType.CondimentsAndSauces -> "Condiments and sauces"
    DishType.MainCourse -> "Main course"
    else -> toString()
}