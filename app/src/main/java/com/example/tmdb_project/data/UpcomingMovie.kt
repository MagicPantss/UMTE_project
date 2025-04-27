package com.example.tmdb_project.data

import com.google.gson.annotations.SerializedName

data class UpcomingMovie(
    val id: Int,
    val title: String,
    @SerializedName("release_date") val releaseDate: String,
    val overview: String?,
    @SerializedName("poster_path") val posterPath: String?
)
