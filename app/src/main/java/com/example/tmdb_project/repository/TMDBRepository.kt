package com.example.tmdb_project.repository

import com.example.tmdb_project.api.TMDBApi
import com.example.tmdb_project.data.Trending
import com.example.tmdb_project.data.TrendingAllRes

class TMDBRepository(private val api: TMDBApi) {
    suspend fun getTrending(page: Int = 1): TrendingAllRes {
        return api.getTrending(page = page)
    }

    suspend fun getMovieDetails(movieId: Int): Trending {
        return api.getMovieDetails(movieId = movieId)
    }
}