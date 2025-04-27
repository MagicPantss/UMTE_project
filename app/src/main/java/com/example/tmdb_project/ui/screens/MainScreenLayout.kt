package com.example.tmdb_project.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.tmdb_project.navigation.NavScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenLayout(
    navController: NavHostController,
    header: String,
    content: @Composable () -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showHomeIcon = currentRoute != NavScreen.HomeScreen.route

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(header) },
                navigationIcon = {
                    if (showHomeIcon) {
                        IconButton(
                            onClick = {
                                navController.popBackStack(
                                    NavScreen.HomeScreen.route,
                                    false
                                )
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "Domů"
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(NavScreen.FindScreen.route)
                    }) {
                        Icon(Icons.Default.Search, contentDescription = "Hledat")
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentRoute == NavScreen.FavouriteScreen.route,
                    onClick = { navController.navigate(NavScreen.FavouriteScreen.route) },
                    icon = { Icon(Icons.Default.Favorite, contentDescription = "Oblíbené") },
                    label = { Text("Oblíbené") }
                )
                NavigationBarItem(
                    selected = currentRoute == NavScreen.WatchlistScreen.route,
                    onClick = { navController.navigate(NavScreen.WatchlistScreen.route) },
                    icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Seznam") },
                    label = { Text("Seznam") }
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            content()
        }
    }
}