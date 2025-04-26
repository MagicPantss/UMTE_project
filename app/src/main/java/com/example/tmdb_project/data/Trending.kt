package com.example.tmdb_project.data

import com.google.gson.annotations.SerializedName

data class Trending(
    @SerializedName("adult") val adult: Boolean?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("first_air_date") val firstAirDate: String?,
    @SerializedName("genre_ids") val genreIds: List<Int?>?,
    @SerializedName("id") override val id: Int,
    @SerializedName("media_type") val mediaType: String?,
    @SerializedName("name") override val name: String?,
    @SerializedName("origin_country") val originCountry: List<String?>?,
    @SerializedName("original_language") val originalLanguage: String?,
    @SerializedName("original_name") val originalName: String?,
    @SerializedName("original_title") val originalTitle: String?,
    @SerializedName("overview") override val overview: String?,
    @SerializedName("popularity") val popularity: Double?,
    @SerializedName("poster_path") override val posterPath: String?,
    @SerializedName("release_date") override val releaseDate: String?,
    @SerializedName("title") override val title: String?,
    @SerializedName("video") val video: Boolean?,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("vote_count") val voteCount: Int?
) : MovieItem

