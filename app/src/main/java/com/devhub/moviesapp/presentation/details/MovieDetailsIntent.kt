package com.devhub.moviesapp.presentation.details

import com.devhub.moviesapp.domain.model.Movie

sealed class MovieDetailsIntent {
    data class CheckFavoriteMovie(val movie: Movie) : MovieDetailsIntent()
    data class ToggleFavorite(val movie: Movie, val favorite: Boolean) : MovieDetailsIntent()
    data class GetMovieCast(val movieId: Int) : MovieDetailsIntent()
}