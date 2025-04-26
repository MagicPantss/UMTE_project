package com.example.tmdb_project.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.tmdb_project.api.RetrofitInstance
import com.example.tmdb_project.repository.FavoritesRepository
import com.example.tmdb_project.repository.TMDBRepository
import com.example.tmdb_project.repository.WatchlistRepository
import com.example.tmdb_project.viewmodel.DetailViewModel
import com.example.tmdb_project.viewmodel.DetailViewModelFactory

@Composable
fun DetailScreen(navController: NavHostController, movieId: Int) {
    val viewModel: DetailViewModel = viewModel(
        factory = DetailViewModelFactory(TMDBRepository(RetrofitInstance.api))
    )

    // 1) data
    val movie by viewModel.movieDetail.collectAsState()
    val favorites by FavoritesRepository.favorites.collectAsState()
    val watchlist by WatchlistRepository.watchlist.collectAsState()

    // 2) načti detail
    LaunchedEffect(movieId) {
        viewModel.loadMovieDetail(movieId)
    }

    MainScreenLayout(navController, "Detail") {
        // scrollable kontejner
        val scroll = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scroll)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            movie?.let { m ->
                val isFav = favorites.any { it.id == m.id }
                val inWL  = watchlist.any { it.movie.id == m.id }

                // plakát
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500${m.posterPath}",
                    contentDescription = m.title,
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                // karta s detailem
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = m.title,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text("Release Date: ${m.releaseDate}")
                        m.runtime?.let { Text("Runtime: $it min") }
                        Text("Genres: ${m.genres.joinToString { it.name }}")
                        Text("Status: ${m.status}")
                        m.tagline?.let {
                            Text(
                                text = "“$it”",
                                style = MaterialTheme.typography.bodyMedium
                                    .copy(fontStyle = FontStyle.Italic)
                            )
                        }

                        Text("Overview:", fontWeight = FontWeight.SemiBold)
                        Text(m.overview)

                        Spacer(modifier = Modifier.height(16.dp))

                        // tlačítka
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(
                                onClick = {
                                    if (isFav) FavoritesRepository.remove(m)
                                    else        FavoritesRepository.add(m)
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isFav)
                                        MaterialTheme.colorScheme.primary
                                    else
                                        MaterialTheme.colorScheme.secondary
                                )
                            ) {
                                Text(if (isFav) "Favorited ❤️" else "Add to Favorites")
                            }
                            Button(
                                onClick = {
                                    if (inWL) WatchlistRepository.remove(m)
                                    else      WatchlistRepository.add(m)
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (inWL)
                                        MaterialTheme.colorScheme.primary
                                    else
                                        MaterialTheme.colorScheme.secondary
                                )
                            ) {
                                Text(if (inWL) "In Watchlist 🎬" else "Add to Watchlist")
                            }
                        }
                    }
                }
            } ?: Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 100.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}