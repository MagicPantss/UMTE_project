package com.example.tmdb_project.repository

import com.example.tmdb_project.data.MovieItem
import com.example.tmdb_project.ui.utils.WatchStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


data class WatchlistItem(
    val movie: MovieItem,
    val status: WatchStatus
)

object WatchlistRepository {
    private val _watchlist = MutableStateFlow<List<WatchlistItem>>(emptyList())
    val watchlist: StateFlow<List<WatchlistItem>> = _watchlist

    fun add(movie: MovieItem) {
        _watchlist.value = (_watchlist.value + WatchlistItem(movie, WatchStatus.PLANNED))
            .distinctBy { it.movie.id }
    }

    fun remove(movie: MovieItem) {
        _watchlist.value = _watchlist.value.filterNot { it.movie.id == movie.id }
    }

    fun isInWatchlist(movie: MovieItem): Boolean =
        _watchlist.value.any { it.movie.id == movie.id }

    fun getStatus(movie: MovieItem): WatchStatus? =
        _watchlist.value.firstOrNull { it.movie.id == movie.id }?.status

    fun updateStatus(movie: MovieItem, newStatus: WatchStatus) {
        _watchlist.value = _watchlist.value.map {
            if (it.movie.id == movie.id) it.copy(status = newStatus) else it
        }
    }
}