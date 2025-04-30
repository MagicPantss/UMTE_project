package com.example.tmdb_project.api


import com.example.tmdb_project.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val authInterceptor = Interceptor { chain ->
        val original = chain.request()
        val originalUrl = original.url
        val urlWithKey = originalUrl.newBuilder()
            .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
            .build()
        val newReq = original.newBuilder().url(urlWithKey).build()
        chain.proceed(newReq)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    val api: TMDBApi by lazy {
        Retrofit.Builder()
            .baseUrl(Globals.BASEURL.value)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TMDBApi::class.java)
    }
}