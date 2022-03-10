package ua.zloydi.recipeapp.domain.retrofit

import okhttp3.Interceptor
import okhttp3.Response
import ua.zloydi.recipeapp.utils.NetworkChecker
import java.io.IOException

class NetworkInterceptor(private val networkChecker: NetworkChecker) : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        if(!networkChecker.checkConnection())
            throw IOException("No internet connection")
        return chain.proceed(chain.request())
    }
}