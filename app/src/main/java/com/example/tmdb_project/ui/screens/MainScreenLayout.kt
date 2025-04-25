package com.example.tmdb_project.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
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
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.tmdb_project.navigation.NavScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenLayout(
    navController: NavHostController,
    header: String,
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(header) },
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
                    selected = false,
                    onClick = { navController.navigate(NavScreen.FavouriteScreen.route) },
                    icon = { Icon(Icons.Default.Favorite, contentDescription = "Oblíbené") },
                    label = { Text("Oblíbené") }
                )
                NavigationBarItem(
                    selected = false,
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