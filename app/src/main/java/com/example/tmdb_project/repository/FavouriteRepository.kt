package com.example.tmdb_project.repository

import com.example.tmdb_project.data.Trending
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object FavoritesRepository {
    private val _favorites = MutableStateFlow<List<Trending>>(emptyList())
    val favorites: StateFlow<List<Trending>> = _favorites

    fun add(item: Trending) {
        _favorites.value = (_favorites.value + item).distinctBy { it.id }
    }

    fun remove(item: Trending) {
        _favorites.value = _favorites.value.filterNot { it.id == item.id }
    }

    fun isFavorite(item: Trending): Boolean =
        _favorites.value.any { it.id == item.id }
}