package com.devhub.moviesapp.presentation.favorite_movies

import com.devhub.moviesapp.domain.model.Movie
import com.devhub.moviesapp.utils.UiState

data class FavoriteMovieState(
    val favoriteMovies: UiState<List<Movie>> = UiState.Loading,
    val favoriteMoviesFound: Boolean = false,
)