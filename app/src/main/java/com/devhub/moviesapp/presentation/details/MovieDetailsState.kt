package com.devhub.moviesapp.presentation.details

import com.devhub.moviesapp.domain.model.Movie
import com.devhub.moviesapp.domain.model.MovieCast
import com.devhub.moviesapp.utils.UiState

data class MovieDetailsState(
    val movieState: UiState<Movie> = UiState.Loading,
    val favoriteState: Boolean = false,
    val movieCastState: UiState<List<MovieCast>> = UiState.Loading,
)

