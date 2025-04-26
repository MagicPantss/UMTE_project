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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tmdb_project.data.MovieDetail
import com.example.tmdb_project.data.Trending
import com.example.tmdb_project.data.TVDetail
import com.example.tmdb_project.navigation.NavScreen
import com.example.tmdb_project.repository.FavoritesRepository
import com.example.tmdb_project.repository.WatchlistRepository
import com.example.tmdb_project.ui.utils.TrendingItemCard

@Composable
fun FavouriteScreen(navController: NavHostController) {
    val favorites by FavoritesRepository.favorites.collectAsState()
    val watchlist by WatchlistRepository.watchlist.collectAsState()

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
                    val type = when (item) {
                        is Trending     -> item.mediaType ?: "movie"
                        is MovieDetail  -> "movie"
                        is TVDetail     -> "tv"
                        else            -> "movie"
                    }
                    val isFav = true
                    val inWL  = watchlist.any { it.movie.id == item.id }

                    TrendingItemCard(
                        item = item,
                        isFavorite = isFav,
                        onFavoriteClick  = { FavoritesRepository.remove(item) },
                        isInWatchlist    = inWL,
                        onWatchlistClick = { /* TODO */ },
                        onItemClick      = {
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
