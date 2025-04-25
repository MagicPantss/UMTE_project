package com.example.tmdb_project.ui.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tmdb_project.data.Trending
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite

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
            .clickable { onItemClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = item.title ?: item.name ?: "Bez názvu", style = MaterialTheme.typography.titleMedium)

            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${item.posterPath}",
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            item.firstAirDate?.let {
                Text(text = "Datum: $it", style = MaterialTheme.typography.bodySmall)
            }

            Text(
                text = item.overview?.take(120)?.plus("...") ?: "Bez popisu",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Row {
                IconButton(onClick = onAddToFavorites) {
                    Icon(Icons.Filled.Favorite, contentDescription = "Přidat do oblíbených")
                }
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = onAddToWatchlist) {
                    Icon(Icons.Filled.Add, contentDescription = "Přidat do watchlistu")
                }
            }
        }
    }
}