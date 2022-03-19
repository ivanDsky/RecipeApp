package ua.zloydi.recipeapp.data.retrofit

class RetrofitService(private val api: RecipeApi) {

    companion object {
        private val DEFAULT_FILTERS = arrayOf(
            "uri",
            "label",
            "image",
            "source",
            "url",
            "ingredients",
            "calories",
            "totalTime",
            "totalWeight",
            "cuisineType",
            "mealType",
            "dishType",
        )
    }

    suspend fun query(
        query: RecipeQuery,
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
}
