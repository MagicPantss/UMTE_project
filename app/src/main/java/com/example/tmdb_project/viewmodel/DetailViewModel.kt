package com.example.tmdb_project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb_project.data.MovieDetail
import com.example.tmdb_project.data.TVDetail
import com.example.tmdb_project.repository.TMDBRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: TMDBRepository) : ViewModel() {
    private val _movieDetail = MutableStateFlow<MovieDetail?>(null)
    val movieDetail: StateFlow<MovieDetail?> = _movieDetail

    private val _tvDetail = MutableStateFlow<TVDetail?>(null)
    val tvDetail: StateFlow<TVDetail?> = _tvDetail

    fun loadMovieDetail(movieId: Int) = viewModelScope.launch {
        _movieDetail.value = repository.getMovieDetails(movieId)
    }

    fun loadTvDetail(tvId: Int) = viewModelScope.launch {
        _tvDetail.value = repository.getTvDetails(tvId)
    }
}