package ua.zloydi.recipeapp.models.filter_types

sealed interface FilterType : java.io.Serializable{
    val label: String
}

class Mapper<T : FilterType>(values: List<T>){
    private val stringToType: HashMap<String, T> = hashMapOf()
    private val typeToString: HashMap<T, String> = hashMapOf()
    init {
        values.forEach {
            stringToType[it.label.lowercase()] = it
            typeToString[it] = it.label
        }
    }
    operator fun get(index: String) = stringToType[index] ?: throw IllegalArgumentException("Unknown type name $index")
    operator fun get(index: T) = typeToString[index] ?: throw IllegalArgumentException("Unknown type")
}

private inline fun <reified T> values() = T::class.sealedSubclasses
    .mapNotNull { it.objectInstance }

sealed class Dish(override val label: String) : FilterType {
    object AlcoholCocktail : Dish("Alcohol-cocktail")
    object BiscuitsAndCookies : Dish("Biscuits and cookies")
    object Bread : Dish("Bread")
    object Cereals : Dish("Cereals")
    object CondimentsAndSauces : Dish("Condiments and sauces")
    object Drinks : Dish("Drinks")
    object Desserts : Dish("Desserts")
    object Egg : Dish("Egg")
    object MainCourse : Dish("Main course")
    object Omelet : Dish("Omelet")
    object Pancake : Dish("Pancake")
    object Preps : Dish("Preps")
    object Preserve : Dish("Preserve")
    object Salad : Dish("Salad")
    object Sandwiches : Dish("Sandwiches")
    object Soup : Dish("Soup")
    object Starter : Dish("Starter")
    companion object{
        val values = values<Dish>()
        val mapper = Mapper(values)
    }
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Dish) return false

        if (label != other.label) return false

        return true
    }

    override fun hashCode() = label.hashCode()
}

sealed class Cuisine(override val label: String) : FilterType {
    object American : Cuisine("American")
    object Asian : Cuisine("Asian")
    object British : Cuisine("British")
    object Caribbean : Cuisine("Caribbean")
    object CentralEurope : Cuisine("Central Europe")
    object Chinese : Cuisine("Chinese")
    object EasternEurope : Cuisine("Eastern Europe")
    object French : Cuisine("French")
    object Indian : Cuisine("Indian")
    object Italian : Cuisine("Italian")
    object Japanese : Cuisine("Japanese")
    object Kosher : Cuisine("Kosher")
    object Mediterranean : Cuisine("Mediterranean")
    object Mexican : Cuisine("Mexican")
    object MiddleEastern : Cuisine("Middle Eastern")
    object Nordic : Cuisine("Nordic")
    object SouthAmerican : Cuisine("South American")
    object SouthEastAsian : Cuisine("South East Asian")
    object World : Cuisine("World")
    companion object{
        val values = values<Cuisine>()
        val mapper = Mapper(values)
    }
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Cuisine) return false

        if (label != other.label) return false

        return true
    }

    override fun hashCode() = label.hashCode()
}

sealed class Meal(override val label: String) : FilterType {
    object Breakfast : Meal("Breakfast")
    object Lunch : Meal("Lunch")
    object Dinner : Meal("Dinner")
    object Snack : Meal("Snack")
    object Teatime : Meal("Teatime")
    companion object{
        val values = values<Meal>()
        val mapper = Mapper(values)
    }
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Meal) return false

        if (label != other.label) return false

        return true
    }

    override fun hashCode() = label.hashCode()
}