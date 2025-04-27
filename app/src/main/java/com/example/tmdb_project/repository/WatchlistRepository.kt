package com.example.tmdb_project.repository

import com.example.tmdb_project.TMDBApplication
import com.example.tmdb_project.data.MovieItem
import com.example.tmdb_project.data.WatchlistItem
import com.example.tmdb_project.data.local.WatchlistEntity
import com.example.tmdb_project.data.local.WatchlistDao
import com.example.tmdb_project.ui.utils.WatchStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

object WatchlistRepository {
    private val dao: WatchlistDao = TMDBApplication.db.watchlistDao()

    val watchlist: Flow<List<WatchlistItem>> = dao.getAll().map { entities ->
        entities.map { e ->
            WatchlistItem(
                movie = object : MovieItem {
                    override val id = e.id
                    override val name: String? = null
                    override val title = e.title
                    override val posterPath = e.posterPath
                    override val overview = e.overview
                    override val releaseDate = e.releaseDate
                },
                status = e.status
            )
        }
    }

    suspend fun add(movie: MovieItem) = withContext(Dispatchers.IO) {
        dao.insert(
            WatchlistEntity(
                id = movie.id,
                mediaType = "",
                title = movie.title,
                posterPath = movie.posterPath,
                overview = movie.overview,
                releaseDate = movie.releaseDate,
                status = WatchStatus.PLANNED
            )
        )
    }

    suspend fun remove(movie: MovieItem) = withContext(Dispatchers.IO) {
        dao.delete(
            WatchlistEntity(
                id = movie.id,
                mediaType = "",
                title = null,
                posterPath = null,
                overview = null,
                releaseDate = null,
                status = WatchStatus.PLANNED
            )
        )
    }

    suspend fun updateStatus(movie: MovieItem, newStatus: WatchStatus) = withContext(Dispatchers.IO) {
        dao.updateStatus(movie.id, newStatus)
    }
}