package ua.zloydi.recipeapp.domain.retrofit

import ua.zloydi.recipeapp.data.filter_types.cuisineMapper
import ua.zloydi.recipeapp.data.filter_types.dishMapper
import ua.zloydi.recipeapp.data.filter_types.mealMapper

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
            cuisineType = cuisineMapper.string(query.cuisineType),
            dishType = dishMapper.string(query.dishType),
            mealType = mealMapper.string(query.mealType),
        )
    }
}
