package com.example.tmdb_project.repository

import com.example.tmdb_project.TMDBApplication
import com.example.tmdb_project.data.MovieItem
import com.example.tmdb_project.data.local.FavouriteEntity
import com.example.tmdb_project.data.local.FavouriteDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

object FavoritesRepository {
    private val dao: FavouriteDao = TMDBApplication.db.favouriteDao()

    val favorites: Flow<List<MovieItem>> = dao.getAll().map { entities ->
        entities.map { e ->
            object : MovieItem {
                override val id = e.id
                override val name: String? = null
                override val title = e.title
                override val posterPath = e.posterPath
                override val overview = e.overview
                override val releaseDate = e.releaseDate
            }
        }
    }

    suspend fun add(item: MovieItem) = withContext(Dispatchers.IO) {
        dao.insert(
            FavouriteEntity(
                id = item.id,
                mediaType = (item as? com.example.tmdb_project.data.Trending)?.mediaType
                    ?: if (item is com.example.tmdb_project.data.MovieDetail) "movie" else "tv",
                title = item.title,
                posterPath = item.posterPath,
                overview = item.overview,
                releaseDate = item.releaseDate
            )
        )
    }

    suspend fun remove(item: MovieItem) = withContext(Dispatchers.IO) {
        dao.delete(
            FavouriteEntity(
                id = item.id,
                mediaType = "",
                title = null,
                posterPath = null,
                overview = null,
                releaseDate = null
            )
        )
    }
}