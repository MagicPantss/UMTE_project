package com.example.tmdb_project.api

import com.example.tmdb_project.data.TrendingAllRes
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBApi {

    @GET("trending/all/week")
    suspend fun getTrending(
        @Query("api_key") apiKey: String = "f70275ecba3f8587cee024daba1926b0",
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("page") page: Int = 1
    ): TrendingAllRes

}