package ua.zloydi.recipeapp.data.retrofit

class RetrofitService(private val api: RecipeApi) {

    companion object {
        private val DEFAULT_FILTERS = listOf(
            "uri",
            "label",
            "image",
            "totalTime",
        )
        private val DETAIL_FILTERS = listOf(
            "uri",
            "source",
            "url",
            "ingredients",
            "cuisineType",
            "mealType",
            "dishType",
        )
    }

    suspend fun query(
        query: RecipeQuery.Search,
        nextHash: String? = null
    ) = with(query) {
        api.queryFilters(
            query = searchFilter.search,
            fields = DEFAULT_FILTERS,
            nextHash = nextHash,
            cuisineType = searchFilter.filter.cuisines.map { it.label },
            dishType = searchFilter.filter.categories.map { it.label },
            mealType = searchFilter.filter.meals.map { it.label },
        )
    }

    suspend fun query(
        query: RecipeQuery.Recipe
    ) = api.queryFilters(
        id = query.id,
        fields = DETAIL_FILTERS
    )
}
