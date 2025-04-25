package com.example.tmdb_project.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.tmdb_project.navigation.NavScreen

@Composable
fun WatchlistScreen(navController: NavHostController) {
    MainScreenLayout(navController, "") {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Toto je čtvrtá obrazovka")
            Button(onClick = { navController.popBackStack(NavScreen.HomeScreen.route, false) }) {
                Text("Zpět na první")
            }
        }
    }
}