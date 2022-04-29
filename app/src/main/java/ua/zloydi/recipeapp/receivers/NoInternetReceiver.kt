package ua.zloydi.recipeapp.receivers

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class NoInternetReceiver : BroadcastReceiver() {
    private val _isInternedConnected = Channel<Boolean>()
    val isInternetConnected = _isInternedConnected.receiveAsFlow()
    private val scope = CoroutineScope(Dispatchers.IO)
    private var connectivityManager: ConnectivityManager? = null

    override fun onReceive(context: Context, intent: Intent?) {
        if (connectivityManager == null)
            connectivityManager =
                context.getSystemService(Activity.CONNECTIVITY_SERVICE) as ConnectivityManager
        scope.launch {
            delay(350) // For syncing internet changing
            _isInternedConnected.send(connectivityManager.isOnline())
        }
    }

    private fun ConnectivityManager?.isOnline(): Boolean{
        if(this == null) return false
        val capabilities = getNetworkCapabilities(activeNetwork) ?: return false
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
    }
}