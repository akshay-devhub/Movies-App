package com.devhub.moviesapp.presentation.home

sealed class HomeIntent {
    object LoadTrendingMovies : HomeIntent()
    object LoadNowPlayingMovies : HomeIntent()
}
