package ua.zloydi.recipeapp.models.filterTypes

data class SearchFilter(
    val search: String = "",
    val filter: Filter = Filter()
) : java.io.Serializable{
    fun isEmpty() = search.isBlank() && filter.isEmpty()
}