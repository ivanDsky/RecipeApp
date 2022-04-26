package ua.zloydi.recipeapp.models.filter_types

data class SearchFilter(
    val search: String = "",
    val filter: Filter = Filter()
){
    fun isEmpty() = search.isBlank() && filter.isEmpty()
}