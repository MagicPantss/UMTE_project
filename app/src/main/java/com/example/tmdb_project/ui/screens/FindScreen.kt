package com.example.tmdb_project.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tmdb_project.api.RetrofitInstance
import com.example.tmdb_project.navigation.NavScreen
import com.example.tmdb_project.repository.FavoritesRepository
import com.example.tmdb_project.repository.TMDBRepository
import com.example.tmdb_project.repository.WatchlistRepository
import com.example.tmdb_project.ui.utils.TrendingItemCard
import com.example.tmdb_project.viewmodel.SearchViewModel
import com.example.tmdb_project.viewmodel.SearchViewModelFactory
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope

@Composable
fun FindScreen(navController: NavHostController) {
    val repo = TMDBRepository(RetrofitInstance.api)
    val vm: SearchViewModel = viewModel(
        factory = SearchViewModelFactory(repo)
    )
    val scope = rememberCoroutineScope()

    val results by vm.results.collectAsState()
    val page by vm.page.collectAsState()
    val total by vm.totalPages.collectAsState()

    var query by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("movie") }

    val favorites by FavoritesRepository.favorites.collectAsState(initial = emptyList())
    val watchlist by WatchlistRepository.watchlist.collectAsState(initial = emptyList())

    MainScreenLayout(navController, "Search") {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    modifier = Modifier.weight(1f),
                    label = { Text("Query") }
                )
                Spacer(Modifier.width(8.dp))
                Button(
                    onClick = { type = "movie" },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (type == "movie") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                    )
                ) { Text("Movie") }
                Spacer(Modifier.width(4.dp))
                Button(
                    onClick = { type = "tv" },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (type == "tv") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                    )
                ) { Text("TV") }
                Spacer(Modifier.width(8.dp))
                Button(onClick = { vm.search(type, query, 1) }) { Text("Search") }
            }

            Spacer(Modifier.height(16.dp))

            if (results.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No results")
                }
            } else {
                LazyColumn(Modifier.weight(1f)) {
                    items(results) { item ->
                        val isFav = favorites.any { it.id == item.id }
                        val inWL = watchlist.any { it.movie.id == item.id }

                        TrendingItemCard(
                            item = item,
                            isFavorite = isFav,
                            onFavoriteClick = {
                                scope.launch {
                                    if (isFav) FavoritesRepository.remove(item)
                                    else        FavoritesRepository.add(item)
                                }
                            },
                            isInWatchlist = inWL,
                            onWatchlistClick = {
                                scope.launch {
                                    if (inWL) WatchlistRepository.remove(item)
                                    else      WatchlistRepository.add(item)
                                }
                            },
                            onItemClick = {
                                navController.navigate(
                                    NavScreen.DetailScreen.createRoute(type, item.id)
                                )
                            }
                        )
                    }
                }

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { if (page > 1) vm.search(type, query, page - 1) },
                        enabled = page > 1
                    ) { Text("Prev") }
                    Text("Page $page / $total")
                    Button(
                        onClick = { if (page < total) vm.search(type, query, page + 1) },
                        enabled = page < total
                    ) { Text("Next") }
                }
            }
        }
    }
}