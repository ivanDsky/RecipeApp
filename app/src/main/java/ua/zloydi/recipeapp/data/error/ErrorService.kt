package ua.zloydi.recipeapp.data.error

import android.util.Log
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel

class ErrorService {
    private val errorChannel = Channel<Error>()

    suspend fun submitError(error: Error){
        Log.e("Debug141", "submitError: ${error.message}")
        errorChannel.send(error)
    }

    fun getErrors(): ReceiveChannel<Error> = errorChannel
}