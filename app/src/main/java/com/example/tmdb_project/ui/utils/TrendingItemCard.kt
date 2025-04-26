package com.example.tmdb_project.ui.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tmdb_project.data.Trending
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.Alignment

@Composable
fun TrendingItemCard(
    item: Trending,
    onAddToFavorites: () -> Unit,
    onAddToWatchlist: () -> Unit,
    onItemClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onItemClick() },
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.padding(12.dp)
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${item.posterPath}",
                contentDescription = null,
                modifier = Modifier
                    .width(100.dp)
                    .height(150.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Top)
            ) {
                Text(
                    text = item.title ?: item.name ?: "No title",
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = item.firstAirDate ?: item.releaseDate ?: "Unknown date",
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = item.overview?.take(100)?.plus("...") ?: "No description",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 4
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    IconButton(onClick = onAddToFavorites) {
                        Icon(Icons.Filled.Favorite, contentDescription = "Add to favorites")
                    }
                    IconButton(onClick = onAddToWatchlist) {
                        Icon(Icons.Filled.Add, contentDescription = "Add to watchlist")
                    }
                }
            }
        }
    }
}