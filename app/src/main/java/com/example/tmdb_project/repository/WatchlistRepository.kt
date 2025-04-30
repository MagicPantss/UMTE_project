package com.example.tmdb_project.repository

import com.example.tmdb_project.TMDBApplication
import com.example.tmdb_project.data.MovieItem
import com.example.tmdb_project.data.Trending
import com.example.tmdb_project.data.local.WatchlistEntity
import com.example.tmdb_project.data.local.WatchlistDao
import com.example.tmdb_project.data.WatchlistItem
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
                    override val name: String?  = if (e.mediaType == "tv") e.title else null
                    override val title: String? = if (e.mediaType == "movie") e.title else null
                    override val posterPath = e.posterPath
                    override val overview   = e.overview
                    override val releaseDate = e.releaseDate
                },
                status = e.status
            )
        }
    }

    suspend fun add(movie: MovieItem) = withContext(Dispatchers.IO) {
        val mediaType = (movie as? Trending)?.mediaType
            ?: if (movie is com.example.tmdb_project.data.MovieDetail) "movie" else "tv"

        val titleValue = movie.title ?: movie.name

        val dateValue = when (movie) {
            is Trending -> movie.releaseDate ?: movie.firstAirDate
            else        -> movie.releaseDate
        }

        dao.insert(
            WatchlistEntity(
                id          = movie.id,
                mediaType   = mediaType,
                title       = titleValue,
                posterPath  = movie.posterPath,
                overview    = movie.overview,
                releaseDate = dateValue,
                status      = WatchStatus.PLANNED
            )
        )
    }

    suspend fun remove(movie: MovieItem) = withContext(Dispatchers.IO) {
        dao.delete(
            WatchlistEntity(
                id          = movie.id,
                mediaType   = "",
                title       = null,
                posterPath  = null,
                overview    = null,
                releaseDate = null,
                status      = WatchStatus.PLANNED
            )
        )
    }

    suspend fun updateStatus(movie: MovieItem, newStatus: WatchStatus) = withContext(Dispatchers.IO) {
        dao.updateStatus(movie.id, newStatus)
    }
}