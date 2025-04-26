package com.example.tmdb_project.repository

import com.example.tmdb_project.data.MovieItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object FavoritesRepository {
    private val _favorites = MutableStateFlow<List<MovieItem>>(emptyList())
    val favorites: StateFlow<List<MovieItem>> = _favorites

    fun add(item: MovieItem) {
        _favorites.value = (_favorites.value + item).distinctBy { it.id }
    }

    fun remove(item: MovieItem) {
        _favorites.value = _favorites.value.filterNot { it.id == item.id }
    }

    fun isFavorite(item: MovieItem): Boolean =
        _favorites.value.any { it.id == item.id }
}