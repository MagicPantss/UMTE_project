package com.example.tmdb_project.data

import com.google.gson.annotations.SerializedName


data class TVDetail(
    override val id: Int,
    @SerializedName("poster_path") override val posterPath: String?,
    @SerializedName("name")
    override val name: String?,
    override val title: String? = name,
    @SerializedName("overview")           override val overview: String?,
    @SerializedName("first_air_date")     override val releaseDate: String?,
    val genres: List<Genre>,
    @SerializedName("number_of_seasons")  val numberOfSeasons: Int?,
    @SerializedName("number_of_episodes") val numberOfEpisodes: Int?
) : MovieItem

