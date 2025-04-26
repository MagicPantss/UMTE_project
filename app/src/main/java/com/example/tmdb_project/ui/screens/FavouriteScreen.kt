package com.example.tmdb_project.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tmdb_project.navigation.NavScreen
import com.example.tmdb_project.repository.FavoritesRepository
import com.example.tmdb_project.ui.utils.TrendingItemCard

@Composable
fun FavouriteScreen(navController: NavHostController) {
    val favorites by FavoritesRepository.favorites.collectAsState()

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
                    val isFav = favorites.any { it.id == item.id }
                    TrendingItemCard(
                        item = item,
                        isFavorite = isFav,
                        onFavoriteClick = { FavoritesRepository.remove(item) },
                        onAddToWatchlist = { /* TODO */ },
                        onItemClick = {
                            navController.navigate(NavScreen.DetailScreen.createRoute(item.id ?: 0))
                        }
                    )
                }
            }
        }
    }
}