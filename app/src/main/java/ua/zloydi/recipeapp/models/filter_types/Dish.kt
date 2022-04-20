package ua.zloydi.recipeapp.models.filter_types

enum class Dish(override val label: String) : FilterType{
    AlcoholCocktail("Alcohol-cocktail"),
    BiscuitsAndCookies("Biscuits and cookies"),
    Bread("Bread"),
    Cereals("Cereals"),
    CondimentsAndSauces("Condiments and sauces"),
    Drinks("Drinks"),
    Desserts("Desserts"),
    Egg("Egg"),
    MainCourse("Main course"),
    Omelet("Omelet"),
    Pancake("Pancake"),
    Preps("Preps"),
    Preserve("Preserve"),
    Salad("Salad"),
    Sandwiches("Sandwiches"),
    Soup("Soup"),
    Starter("Starter"),
}

object DishMapper : FilterTypeMapper<Dish>(){
    override fun values() = Dish.values()
}
