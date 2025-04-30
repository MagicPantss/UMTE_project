package com.example.tmdb_project.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tmdb_project.navigation.NavScreen
import com.example.tmdb_project.repository.FavoritesRepository
import com.example.tmdb_project.repository.WatchlistRepository
import com.example.tmdb_project.ui.utils.TrendingItemCard
import kotlinx.coroutines.launch

@Composable
fun FavouriteScreen(navController: NavHostController) {
    val scope = rememberCoroutineScope()
    val favorites by FavoritesRepository.favorites.collectAsState(initial = emptyList())
    val watchlist by WatchlistRepository.watchlist.collectAsState(initial = emptyList())

    MainScreenLayout(navController, "Favourites") {
        if (favorites.isEmpty()) {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Nemáte žádné oblíbené položky.")
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(favorites) { item ->
                    // Pokud má item.name hodnotu, je to TV, jinak movie
                    val type = if (item.name != null) "tv" else "movie"
                    val isFav = true
                    val inWL  = watchlist.any { it.movie.id == item.id }

                    TrendingItemCard(
                        item = item,
                        isFavorite     = isFav,
                        onFavoriteClick= {
                            scope.launch {
                                if (isFav) FavoritesRepository.remove(item)
                                else        FavoritesRepository.add(item)
                            }
                        },
                        isInWatchlist  = inWL,
                        onWatchlistClick = {
                            scope.launch {
                                if (inWL) WatchlistRepository.remove(item)
                                else      WatchlistRepository.add(item)
                            }
                        },
                        onItemClick    = {
                            navController.navigate(
                                NavScreen.DetailScreen.createRoute(type, item.id)
                            )
                        }
                    )
                }
            }
        }
    }
}
