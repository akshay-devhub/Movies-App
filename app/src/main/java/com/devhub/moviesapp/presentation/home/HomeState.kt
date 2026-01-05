package com.devhub.moviesapp.presentation.home

import androidx.paging.PagingData
import com.devhub.moviesapp.domain.model.Movie
import com.devhub.moviesapp.utils.UiState
import kotlinx.coroutines.flow.Flow

data class HomeState(


    val nowPlayingMovies: UiState<Flow<PagingData<Movie>>> = UiState.Loading,
    val trendingMovies: UiState<Flow<PagingData<Movie>>> = UiState.Loading,


    )
