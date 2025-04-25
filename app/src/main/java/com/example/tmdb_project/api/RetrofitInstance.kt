package com.example.tmdb_project.api


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: TMDBApi by lazy {
        Retrofit.Builder()
            .baseUrl(Globals.BASEURL.value)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TMDBApi::class.java)
    }
}