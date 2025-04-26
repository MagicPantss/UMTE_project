package com.example.tmdb_project.repository

import com.example.tmdb_project.api.TMDBApi
import com.example.tmdb_project.data.MovieDetail
import com.example.tmdb_project.data.TVDetail
import com.example.tmdb_project.data.TrendingAllRes

class TMDBRepository(private val api: TMDBApi) {
    suspend fun getTrending(page: Int = 1): TrendingAllRes =
        api.getTrending(page = page)

    suspend fun getMovieDetails(movieId: Int): MovieDetail =
        api.getMovieDetails(movieId)

    suspend fun getTvDetails(tvId: Int): TVDetail =
        api.getTvDetails(tvId)
}