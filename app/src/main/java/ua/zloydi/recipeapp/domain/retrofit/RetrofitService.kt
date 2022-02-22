package ua.zloydi.recipeapp.domain.retrofit

import ua.zloydi.recipeapp.data.filter_types.getString

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
            cuisineType = cuisineType?.getString(),
            mealType = mealType?.getString(),
            dishType = dishType?.getString(),
        )
    }
}
