package ua.zloydi.recipeapp.domain.error

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import ua.zloydi.recipeapp.data.errors.Error

class ErrorService {
    private val errorChannel = Channel<Error>()

    suspend fun submitError(error: Error) = errorChannel.send(error)

    fun getErrors(): ReceiveChannel<Error> = errorChannel
}