package com.example.tmdb_project.api

import com.example.tmdb_project.data.MovieDetail
import com.example.tmdb_project.data.TVDetail
import com.example.tmdb_project.data.TrendingAllRes
import com.example.tmdb_project.data.UpcomingResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {

    @GET("trending/all/week")
    suspend fun getTrending(
        @Query("api_key") apiKey: String = "f70275ecba3f8587cee024daba1926b0",
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("page") page: Int = 1
    ): TrendingAllRes

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = "f70275ecba3f8587cee024daba1926b0",
        @Query("language") language: String = "en-US"
    ): MovieDetail

    @GET("tv/{tv_id}")
    suspend fun getTvDetails(
        @Path("tv_id") tvId: Int,
        @Query("api_key") apiKey: String = "f70275ecba3f8587cee024daba1926b0",
        @Query("language") language: String = "en-US"
    ): TVDetail

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = "f70275ecba3f8587cee024daba1926b0",
        @Query("language") language: String = "en-US",
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("page") page: Int = 1
    ): TrendingAllRes

    @GET("search/tv")
    suspend fun searchTv(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = "f70275ecba3f8587cee024daba1926b0",
        @Query("language") language: String = "en-US",
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("page") page: Int = 1
    ): TrendingAllRes

    @GET("movie/upcoming")
    suspend fun getUpcoming(
        @Query("api_key") apiKey: String = "f70275ecba3f8587cee024daba1926b0",
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): UpcomingResponse

}