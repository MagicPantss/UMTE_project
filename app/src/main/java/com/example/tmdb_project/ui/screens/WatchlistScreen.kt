package com.example.tmdb_project.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.tmdb_project.data.WatchlistItem
import com.example.tmdb_project.navigation.NavScreen
import com.example.tmdb_project.repository.WatchlistRepository
import com.example.tmdb_project.ui.utils.WatchStatus
import kotlinx.coroutines.launch

@Composable
fun WatchlistScreen(navController: NavHostController) {
    val watchlist by WatchlistRepository.watchlist.collectAsState(initial = emptyList())

    MainScreenLayout(navController, "Watchlist") {
        if (watchlist.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Žádné položky ve watchlistu.")
            }
        } else {
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(watchlist) { item ->
                    WatchlistItemRow(
                        item = item,
                        navController = navController
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchlistItemRow(
    item: WatchlistItem,
    navController: NavHostController
) {
    val scope = rememberCoroutineScope()
    var expanded by remember { mutableStateOf(false) }

    val type = if (item.movie.name != null) "tv" else "movie"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                navController.navigate(
                    NavScreen.DetailScreen.createRoute(type, item.movie.id)
                )
            },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w200${item.movie.posterPath}",
                contentDescription = item.movie.title ?: item.movie.name,
                modifier = Modifier
                    .size(width = 80.dp, height = 120.dp)
            )

            Spacer(Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.movie.title ?: item.movie.name.orEmpty(),
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2
                )

                Spacer(Modifier.height(8.dp))

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = item.status.name,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Status") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .widthIn(min = 100.dp, max = 250.dp)
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        WatchStatus.values().forEach { status ->
                            DropdownMenuItem(
                                text = { Text(status.name) },
                                onClick = {
                                    expanded = false
                                    scope.launch {
                                        WatchlistRepository.updateStatus(item.movie, status)
                                    }
                                }
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.width(8.dp))

            IconButton(onClick = {
                scope.launch { WatchlistRepository.remove(item.movie) }
            }) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Odebrat z watchlistu"
                )
            }
        }
    }
}