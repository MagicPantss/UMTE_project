package com.example.tmdb_project.navigation

sealed class NavScreen(val route: String) {
    object HomeScreen : NavScreen("home_screen")
    object FindScreen : NavScreen("find_screen")
    object FavouriteScreen : NavScreen("favourite_screen")
    object WatchlistScreen : NavScreen("watchlist_screen")
}