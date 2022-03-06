package ua.zloydi.recipeapp.data.filter_types

import java.util.*

abstract class TypeMapper<T:Enum<T>> {
    protected abstract val customNames: Array<Pair<T,String>>
    protected abstract fun allName() : Array<T>

    private val enumToString = buildMap{
        putAll(customNames)
        allName().forEach {name ->
            getOrPut(name){name.toString()}
            Unit
        }
    }

    private val stringToEnum = TreeMap<String,T>(String.CASE_INSENSITIVE_ORDER).apply {
        enumToString.forEach { put(it.value,it.key) }
    }

    fun enum(name: String?) = name?.let {stringToEnum[it.lowercase()]}

    fun string(enum: T?) = enum?.let{enumToString[it]}
}