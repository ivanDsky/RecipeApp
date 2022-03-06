package ua.zloydi.recipeapp.data.errors

sealed class Error{
    abstract val message: String
    data class MessageError(override val message: String) : Error()
}
