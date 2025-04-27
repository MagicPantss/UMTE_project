package com.example.tmdb_project.data

import com.example.tmdb_project.ui.utils.DateRange
import com.google.gson.annotations.SerializedName

data class UpcomingResponse(
    val page: Int,
    val results: List<UpcomingMovie>,
    val dates: DateRange,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)