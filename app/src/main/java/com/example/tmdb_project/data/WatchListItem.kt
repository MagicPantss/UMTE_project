package com.example.tmdb_project.data

import com.example.tmdb_project.ui.utils.WatchStatus

data class WatchlistItem(
    val movie: MovieItem,
    val status: WatchStatus
)