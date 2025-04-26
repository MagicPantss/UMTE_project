package com.example.tmdb_project.repository

import com.example.tmdb_project.data.Trending
import com.example.tmdb_project.ui.utils.WatchStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class WatchlistItem(val movie: Trending, val status: WatchStatus)

object WatchlistRepository {
    private val _watchlist = MutableStateFlow<List<WatchlistItem>>(emptyList())
    val watchlist: StateFlow<List<WatchlistItem>> = _watchlist

    fun add(movie: Trending) {
        // default
        _watchlist.value = (_watchlist.value + WatchlistItem(movie, WatchStatus.PLANNED))
            .distinctBy { it.movie.id }
    }

    fun remove(movie: Trending) {
        _watchlist.value = _watchlist.value.filterNot { it.movie.id == movie.id }
    }

    fun isInWatchlist(movie: Trending): Boolean =
        _watchlist.value.any { it.movie.id == movie.id }

    fun getStatus(movie: Trending): WatchStatus? =
        _watchlist.value.firstOrNull { it.movie.id == movie.id }?.status

    fun updateStatus(movie: Trending, newStatus: WatchStatus) {
        _watchlist.value = _watchlist.value.map {
            if (it.movie.id == movie.id) it.copy(status = newStatus) else it
        }
    }
}