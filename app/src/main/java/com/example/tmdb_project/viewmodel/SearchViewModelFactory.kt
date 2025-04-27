package com.example.tmdb_project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tmdb_project.repository.TMDBRepository

class SearchViewModelFactory(private val repo: TMDBRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(repo) as T
    }
}