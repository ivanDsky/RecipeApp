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

fun CuisineType.getString() = when(this){
	CuisineType.CentralEurope -> "Central Europe"
	CuisineType.EasternEurope -> "Eastern Europe"
	CuisineType.MiddleEastern -> "Middle Eastern"
	CuisineType.SouthAmerican -> "South American"
	CuisineType.SouthEastAsian -> "South East Asian"
	else -> this.toString()
}