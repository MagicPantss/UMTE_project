package com.example.tmdb_project.data

import com.google.gson.annotations.SerializedName


data class MovieDetail(
    val adult: Boolean,
    @SerializedName("backdrop_path") override val posterPath: String?,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String?,
    override val id: Int,
    @SerializedName("imdb_id") val imdbId: String?,
    @SerializedName("origin_country") val originCountry: List<String>,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("overview") override val overview: String,
    val popularity: Double,
    @SerializedName("release_date") override val releaseDate: String,
    val revenue: Int,
    val runtime: Int?,
    val status: String,
    val tagline: String?,
    override val title: String,
    override val name: String? = null,
    val video: Boolean,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int
) : MovieItem