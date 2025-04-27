package com.example.tmdb_project.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb_project.data.Trending
import com.example.tmdb_project.repository.TMDBRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel(private val repo: TMDBRepository) : ViewModel() {
    private val _results = MutableStateFlow<List<Trending>>(emptyList())
    val results: StateFlow<List<Trending>> = _results

    private val _page = MutableStateFlow(1)
    val page: StateFlow<Int> = _page

    private val _totalPages = MutableStateFlow(1)
    val totalPages: StateFlow<Int> = _totalPages

    fun search(type: String, query: String, page: Int) {
        viewModelScope.launch {
            val res = if (type == "tv") repo.searchTv(query, page)
            else                 repo.searchMovies(query, page)
            _results.value = res.results?.filterNotNull() ?: emptyList()
            _page.value = res.page ?: page
            _totalPages.value = res.totalPages ?: 1
        }
    }
}