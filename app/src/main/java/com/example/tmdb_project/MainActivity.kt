package com.example.tmdb_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tmdb_project.api.RetrofitInstance
import com.example.tmdb_project.navigation.NavScreen
import com.example.tmdb_project.repository.FavoritesRepository
import com.example.tmdb_project.repository.TMDBRepository
import com.example.tmdb_project.repository.WatchlistRepository
import com.example.tmdb_project.ui.screens.FindScreen
import com.example.tmdb_project.ui.screens.FavouriteScreen
import com.example.tmdb_project.ui.screens.DetailScreen
import com.example.tmdb_project.ui.screens.MainScreenLayout
import com.example.tmdb_project.ui.screens.WatchlistScreen
import com.example.tmdb_project.ui.theme.TMDB_projectTheme
import com.example.tmdb_project.ui.utils.TrendingItemCard
import com.example.tmdb_project.viewmodel.TrendingViewModel
import com.example.tmdb_project.viewmodel.TrendingViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TMDB_projectTheme {
                    AppNavigation()
            }
        }
    }
}

@Composable
fun HomeScreen(navController: NavHostController) {
    val viewModel: TrendingViewModel = viewModel(
        factory = TrendingViewModelFactory(
            TMDBRepository(RetrofitInstance.api)
        )
    )
    val trending by viewModel.trendingList.collectAsState()
    val favorites by FavoritesRepository.favorites.collectAsState()
    val watchlist by WatchlistRepository.watchlist.collectAsState()

    MainScreenLayout(navController, "Discover trending") {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            itemsIndexed(trending.orEmpty().filterNotNull()) { index, item ->
                val isFav = favorites.any { it.id == item.id }
                val inWL = watchlist.any { it.movie.id == item.id }
                TrendingItemCard(
                    item = item,
                    isFavorite = isFav,
                    onFavoriteClick = {
                        if (isFav) FavoritesRepository.remove(item)
                        else       FavoritesRepository.add(item) },
                    isInWatchlist = inWL,                            // ← nový parametr
                    onWatchlistClick = {
                        if (inWL) WatchlistRepository.remove(item)
                        else      WatchlistRepository.add(item)
                    },
                    onItemClick = {
                        //navController.navigate("${NavScreen.DetailScreen.route}/${item.id}")
                        navController.navigate(NavScreen.DetailScreen.createRoute(item.id ?: 0))
                    }
                )

                // Zde spustíme další načtení, pokud jsme blízko konci seznamu
                if (index >= trending!!.size - 5) {
                    viewModel.loadNextPage()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = NavScreen.HomeScreen.route) {
        composable(NavScreen.FindScreen.route) { FindScreen(navController) }
        composable(NavScreen.FavouriteScreen.route) { FavouriteScreen(navController) }
        composable(NavScreen.WatchlistScreen.route) { WatchlistScreen(navController) }
        composable(NavScreen.HomeScreen.route) { HomeScreen(navController) }
        composable(
            route = "detail_screen/{movieId}"
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")?.toIntOrNull() ?: 0
            DetailScreen(navController, movieId)
        }
    }
}