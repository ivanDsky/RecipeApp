package ua.zloydi.recipeapp.models.error

sealed class Error{
    abstract val message: String
    data class MessageError(override val message: String) : Error()
}
