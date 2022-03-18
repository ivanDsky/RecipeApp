package ua.zloydi.recipeapp.data.error

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel

class ErrorService {
    private val errorChannel = Channel<Error>()

    suspend fun submitError(error: Error) = errorChannel.send(error)

    fun getErrors(): ReceiveChannel<Error> = errorChannel
}