package com.example.tmdb_project.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites")
data class FavouriteEntity(
    @PrimaryKey val id: Int,
    val mediaType: String,
    val title: String?,
    val posterPath: String?,
    val overview: String?,
    val releaseDate: String?
)