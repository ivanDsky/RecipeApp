package ua.zloydi.recipeapp.utils

import android.content.Context
import android.net.ConnectivityManager

class NetworkChecker(context: Context) {
    private val service = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    fun checkConnection() : Boolean{
        val info = service.activeNetworkInfo ?: return false
        return info.isConnected
    }
}