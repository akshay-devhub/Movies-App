package com.devhub.moviesapp.presentation.favorite_movies

sealed class FavoriteMovieIntent {
    object GetFavoriteMovies : FavoriteMovieIntent()
}