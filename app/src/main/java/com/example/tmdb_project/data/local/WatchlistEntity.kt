package com.example.tmdb_project.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tmdb_project.ui.utils.WatchStatus

@Entity(tableName = "watchlist")
data class WatchlistEntity(
    @PrimaryKey val id: Int,
    val mediaType: String,
    val title: String?,
    val posterPath: String?,
    val overview: String?,
    val releaseDate: String?,
    val status: WatchStatus
)
