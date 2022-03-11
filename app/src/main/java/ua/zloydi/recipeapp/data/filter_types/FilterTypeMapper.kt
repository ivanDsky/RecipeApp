package ua.zloydi.recipeapp.data.filter_types

abstract class FilterTypeMapper<T> where T : Enum<T>, T : FilterType{
    protected abstract fun values() : Array<T>
    private val stringToEnum = buildMap { values().forEach { put(it.label.lowercase(), it) } }
    fun enum(label: String?) = label?.let {stringToEnum[it.lowercase()]}
}