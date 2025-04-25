package com.example.tmdb_project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb_project.data.Trending
import com.example.tmdb_project.repository.TMDBRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TrendingViewModel(private val repository: TMDBRepository) : ViewModel() {

    private val _trendingList = MutableStateFlow<List<Trending?>>(emptyList())
    val trendingList: StateFlow<List<Trending?>> = _trendingList

    private var currentPage = 1
    private var isLoading = false
    private var isLastPage = false

    init {
        loadNextPage()
    }

    fun loadNextPage() {
        if (isLoading || isLastPage) return

        isLoading = true
        viewModelScope.launch {
            try {
                val response = repository.getTrending(page = currentPage)
                val newItems = response.results ?: emptyList()

                // Pokud je nových výsledků méně než očekávaná stránka (např. 20), jsme na konci
                if (newItems.isEmpty()) {
                    isLastPage = true
                }

                val updatedList = _trendingList.value.toMutableList().apply {
                    addAll(newItems)
                }

                _trendingList.value = updatedList
                currentPage++
            } catch (e: Exception) {
                // Log nebo error handling
            } finally {
                isLoading = false
            }
        }
    }
}