package ua.zloydi.recipeapp.domain.retrofit

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
        query: RecipeQuery
    ) = with(query) {
        api.queryFilters(
            this.query,
            DEFAULT_FILTERS,
            cuisineType = query.cuisineType?.label,
            dishType = query.dishType?.label,
            mealType = query.mealType?.label,
        )
    }
}
