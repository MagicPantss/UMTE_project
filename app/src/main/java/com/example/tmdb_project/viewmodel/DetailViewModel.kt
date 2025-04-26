package com.example.tmdb_project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb_project.data.Trending
import com.example.tmdb_project.repository.TMDBRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: TMDBRepository) : ViewModel() {

    private val _movieDetail = MutableStateFlow<Trending?>(null)
    val movieDetail: StateFlow<Trending?> = _movieDetail

    fun loadMovieDetail(movieId: Int) {
        viewModelScope.launch {
            try {
                val movie = repository.getMovieDetails(movieId)
                _movieDetail.value = movie
            } catch (e: Exception) {
                // Error handling (např. nastavit error message)
            }
        }
    }
}