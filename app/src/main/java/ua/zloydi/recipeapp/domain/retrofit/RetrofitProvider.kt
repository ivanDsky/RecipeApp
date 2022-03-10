package ua.zloydi.recipeapp.domain.retrofit

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ua.zloydi.recipeapp.App
import ua.zloydi.recipeapp.domain.retrofit.RetrofitConstants.BASE_URl
import ua.zloydi.recipeapp.utils.NetworkChecker


object RetrofitProvider {
    private val networkChecker = NetworkChecker(App.instance.applicationContext)
    private val okClient = OkHttpClient.Builder()
        .addInterceptor(NetworkInterceptor(networkChecker))
        .build()

    private val retrofit = Retrofit.Builder()
        .client(okClient)
        .baseUrl(BASE_URl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api: RecipeApi = retrofit.create(RecipeApi::class.java)
    val service = RetrofitService(api)
}