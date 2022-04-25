package ua.zloydi.recipeapp.data.retrofit

class RetrofitService(private val api: RecipeApi) {

    companion object {
        private val DEFAULT_FILTERS = listOf(
            "uri",
            "label",
            "image",
            "totalTime",
            "cuisineType",
            "mealType",
            "dishType",
        )
        private val DETAIL_FILTERS = listOf(
            "uri",
            "source",
            "url",
            "ingredients",
        )
    }

    suspend fun query(
        query: RecipeQuery.Search,
        nextHash: String? = null
    ) = with(query) {
        api.queryFilters(
            query = this.query,
            fields = DEFAULT_FILTERS,
            nextHash = nextHash,
            cuisineType = query.cuisineType.map { it.label },
            dishType = query.dishType.map { it.label },
            mealType = query.mealType.map { it.label },
        )
    }

    suspend fun query(
        query: RecipeQuery.Recipe
    ) = api.queryFilters(
        id = query.id,
        fields = DETAIL_FILTERS
    )
}
