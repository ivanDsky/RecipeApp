package ua.zloydi.recipeapp.data

import android.util.Log
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import ua.zloydi.recipeapp.models.error.Error

class ErrorService {
    private val errorChannel = Channel<Error>()
    val errors = errorChannel.receiveAsFlow()

    suspend fun submitError(error: Error.MessageError){
        Log.e("Debug141", "submitError: ${error.message}")
        errorChannel.send(error)
    }
}

object ErrorProvider {
    val service = ErrorService()
}