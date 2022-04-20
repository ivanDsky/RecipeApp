package ua.zloydi.recipeapp.models.filter_types

enum class Cuisine(override val label: String) : FilterType {
    American("American"),
    Asian("Asian"),
    British("British"),
    Caribbean("Caribbean"),
    CentralEurope("Central Europe"),
    Chinese("Chinese"),
    EasternEurope("Eastern Europe"),
    French("French"),
    Indian("Indian"),
    Italian("Italian"),
    Japanese("Japanese"),
    Kosher("Kosher"),
    Mediterranean("Mediterranean"),
    Mexican("Mexican"),
    MiddleEastern("Middle Eastern"),
    Nordic("Nordic"),
    SouthAmerican("South American"),
    SouthEastAsian("South East Asian"),
}

object CuisineMapper : FilterTypeMapper<Cuisine>(){
    override fun values() = Cuisine.values()
}