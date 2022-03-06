package ua.zloydi.recipeapp.data.filter_types

enum class CuisineType {
    American,
    Asian,
    British,
    Caribbean,
    CentralEurope,
    Chinese,
    EasternEurope,
    French,
    Indian,
    Italian,
    Japanese,
    Kosher,
    Mediterranean,
    Mexican,
    MiddleEastern,
    Nordic,
    SouthAmerican,
    SouthEastAsian,
}

val cuisineMapper by lazy { CuisineMapper() }

class CuisineMapper : TypeMapper<CuisineType>() {
    override fun allName() = CuisineType.values()
    override val customNames: Array<Pair<CuisineType, String>>
        get() = arrayOf(
            CuisineType.CentralEurope to "Central Europe",
            CuisineType.EasternEurope to "Eastern Europe",
            CuisineType.MiddleEastern to "Middle Eastern",
            CuisineType.SouthAmerican to "South American",
            CuisineType.SouthEastAsian to "South East Asian",
        )
}