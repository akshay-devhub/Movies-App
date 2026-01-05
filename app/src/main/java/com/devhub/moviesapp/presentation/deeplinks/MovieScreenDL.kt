package com.devhub.moviesapp.presentation.deeplinks

import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.devhub.moviesapp.presentation.details.MovieDetailsContent
import com.devhub.moviesapp.presentation.details.MovieDetailsContentDeep
import com.devhub.moviesapp.presentation.details.MovieDetailsIntent
import com.devhub.moviesapp.presentation.details.MovieDetailsViewModel

@Composable
fun MovieScreenDL(
    imdbId: String,
    viewModel: MovieDetailsViewModel,
    onBackClick: () -> Unit
) {
    LaunchedEffect(imdbId) {
        viewModel.loadMovieByImdbId(imdbId)
    }

    val uiState by viewModel.uiStateDeep.collectAsState()

    when {
        uiState.isLoading -> {
            Text("Loading...")
        }

        uiState.movie != null -> {
            MovieDetailsContentDeep(
                movie = uiState.movie!!,
                uiState = uiState,
                scrollState = rememberScrollState(),
                onBackClick = onBackClick,
                onFavoriteToggle = { isFavorite ->
                    viewModel.handleIntent(
                        MovieDetailsIntent.ToggleFavorite(
                            movie = uiState.movie!!,
                            favorite = isFavorite
                        )
                    )
                }
            )
        }

        uiState.error != null -> {
            Text(uiState.error!!)
        }
    }

}
