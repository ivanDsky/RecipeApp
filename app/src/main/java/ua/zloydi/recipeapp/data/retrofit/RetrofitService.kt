package ua.zloydi.recipeapp.data.retrofit

class RetrofitService(private val api: RecipeApi) {

    companion object {
        private val DEFAULT_FILTERS = arrayOf(
            "uri",
            "label",
            "image",
            "totalTime",
            "cuisineType",
            "mealType",
            "dishType",
        )
        private val DETAIL_FILTERS = arrayOf(
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
            cuisineType = query.cuisineType?.label,
            dishType = query.dishType?.label,
            mealType = query.mealType?.label,
        )
    }

    suspend fun query(
        query: RecipeQuery.Recipe
    ) = api.queryFilters(
        id = query.id,
        fields = DETAIL_FILTERS
    )

}
