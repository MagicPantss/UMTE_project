package com.example.tmdb_project.data

import com.google.gson.annotations.SerializedName

data class TrendingAllRes(
    @SerializedName("page")
    val page: Int?,

    @SerializedName("results")
    val results: List<Trending?>?,

    @SerializedName("total_pages")
    val totalPages: Int?,

    @SerializedName("total_results")
    val totalResults: Int?
)