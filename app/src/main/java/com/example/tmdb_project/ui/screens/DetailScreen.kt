package com.example.tmdb_project.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.tmdb_project.data.MovieDetail
import com.example.tmdb_project.data.TVDetail
import com.example.tmdb_project.repository.FavoritesRepository
import com.example.tmdb_project.repository.TMDBRepository
import com.example.tmdb_project.repository.WatchlistRepository
import com.example.tmdb_project.viewmodel.DetailViewModel
import com.example.tmdb_project.viewmodel.DetailViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(navController: NavHostController, type: String, itemId: Int) {
    val viewModel: DetailViewModel = viewModel(
        factory = DetailViewModelFactory(
            TMDBRepository(RetrofitInstance.api)
        )
    )
    val movie by viewModel.movieDetail.collectAsState()
    val tv by viewModel.tvDetail.collectAsState()

    LaunchedEffect(type, itemId) {
        if (type == "tv") viewModel.loadTvDetail(itemId)
        else                viewModel.loadMovieDetail(itemId)
    }

    val header = when {
        type == "tv"    -> tv?.title.orEmpty()
        type == "movie" -> movie?.title.orEmpty()
        else             -> "Detail"
    }
    MainScreenLayout(navController, header) {
        when {
            type == "tv" && tv != null -> TvDetailContent(tv!!)
            type == "movie" && movie != null -> MovieDetailContent(movie!!)
            else -> Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
private fun MovieDetailContent(m: MovieDetail) {
    val scope = rememberCoroutineScope()
    val favorites by FavoritesRepository.favorites.collectAsState(initial = emptyList())
    val watchlist by WatchlistRepository.watchlist.collectAsState(initial = emptyList())
    val isFav = favorites.any { it.id == m.id }
    val inWL = watchlist.any { it.movie.id == m.id }
    val scroll = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scroll)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500${m.posterPath}",
            contentDescription = m.title,
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(m.title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text("Release Date: ${m.releaseDate}")
                m.runtime?.let { Text("Runtime: $it min") }
                Text("Genres: ${m.genres.joinToString { it.name }}")
                Text("Status: ${m.status}")
                m.tagline?.let {
                    Text(
                        text = "“$it”",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontStyle = FontStyle.Italic
                        )
                    )
                }
                Text("Overview:", fontWeight = FontWeight.SemiBold)
                Text(m.overview)

                Spacer(Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            scope.launch {
                                if (isFav) FavoritesRepository.remove(m)
                                else        FavoritesRepository.add(m)
                            }
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
                            scope.launch {
                                if (inWL) WatchlistRepository.remove(m)
                                else      WatchlistRepository.add(m)
                            }
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
    }
}

@Composable
private fun TvDetailContent(tv: TVDetail) {
    val scope = rememberCoroutineScope()
    val favorites by FavoritesRepository.favorites.collectAsState(initial = emptyList())
    val watchlist by WatchlistRepository.watchlist.collectAsState(initial = emptyList())
    val isFav = favorites.any { it.id == tv.id }
    val inWL = watchlist.any { it.movie.id == tv.id }
    val scroll = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scroll)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500${tv.posterPath}",
            contentDescription = tv.title,
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(tv.title.orEmpty(), fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text("First Air Date: ${tv.releaseDate ?: "Unknown"}")
                Text("Seasons: ${tv.numberOfSeasons ?: 0}, Episodes: ${tv.numberOfEpisodes ?: 0}")
                Text("Genres: ${tv.genres.joinToString { it.name }}")
                Text("Overview:", fontWeight = FontWeight.SemiBold)
                Text(tv.overview.orEmpty())

                Spacer(Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            scope.launch {
                                if (isFav) FavoritesRepository.remove(tv)
                                else        FavoritesRepository.add(tv)
                            }
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
                            scope.launch {
                                if (inWL) WatchlistRepository.remove(tv)
                                else      WatchlistRepository.add(tv)
                            }
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
    }
}