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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.tmdb_project.api.RetrofitInstance
import com.example.tmdb_project.repository.FavoritesRepository
import com.example.tmdb_project.repository.TMDBRepository
import com.example.tmdb_project.ui.utils.genreMap
import com.example.tmdb_project.viewmodel.DetailViewModel
import com.example.tmdb_project.viewmodel.DetailViewModelFactory

@Composable
fun DetailScreen(navController: NavHostController, movieId: Int) {
    val viewModel: DetailViewModel = viewModel(
        factory = DetailViewModelFactory(
            TMDBRepository(RetrofitInstance.api)
        )
    )
    val movieDetail by viewModel.movieDetail.collectAsState()

    var isFavorite by remember { mutableStateOf(false) }
    var isInWatchlist by remember { mutableStateOf(false) }

    LaunchedEffect(movieDetail) {
        movieDetail?.let { isFavorite = FavoritesRepository.isFavorite(it) }
    }

    LaunchedEffect(movieId) {
        viewModel.loadMovieDetail(movieId)
    }

    MainScreenLayout(navController, "Detail") {
        if (movieDetail != null) {
            val movie = movieDetail!!

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                    contentDescription = movie.title ?: movie.name,
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

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
                            text = movie.title ?: movie.name ?: "No Title",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Release Date: ${movie.releaseDate ?: movie.firstAirDate ?: "Unknown"}",
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Text(
                            text = "Genres: ${
                                movie.genreIds?.joinToString(", ") { id ->
                                    genreMap[id] ?: "Unknown"
                                } ?: "Unknown"
                            }",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(text = "${movie.genreIds}")

                        Text(
                            text = "Original Language: ${movie.originalLanguage?.uppercase() ?: "Unknown"}",
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Text(
                            text = "Average Rating: ${movie.voteAverage?.toString() ?: "N/A"}",
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Text(
                            text = "Vote Count: ${movie.voteCount?.toString() ?: "N/A"}",
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Text(
                            text = "Overview:",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp
                        )

                        Text(
                            text = movie.overview ?: "No overview available",
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // ---- Tlačítka Favorite / Watchlist ----
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(
                                onClick = {
                                    movieDetail?.let {
                                        if (isFavorite) FavoritesRepository.remove(it)
                                        else FavoritesRepository.add(it)
                                        isFavorite = !isFavorite
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                                )
                            ) {
                                Text(if (isFavorite) "Favorited ❤️" else "Add to Favorites")
                            }

                            Button(
                                onClick = { isInWatchlist = !isInWatchlist },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isInWatchlist) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                                )
                            ) {
                                Text(if (isInWatchlist) "In Watchlist 🎬" else "Add to Watchlist")
                            }
                        }
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}