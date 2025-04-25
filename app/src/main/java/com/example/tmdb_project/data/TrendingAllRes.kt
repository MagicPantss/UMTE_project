package com.example.tmdb_project.data

data class TrendingAllRes(
    val page: Int?,
    val results: List<Trending?>?,
    val totalPages: Int?,
    val totalResults: Int?
)