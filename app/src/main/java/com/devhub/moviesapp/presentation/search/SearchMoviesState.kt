package com.devhub.moviesapp.presentation.search

import androidx.paging.PagingData
import com.devhub.moviesapp.domain.model.Movie
import com.devhub.moviesapp.utils.UiState
import kotlinx.coroutines.flow.Flow

data class SearchMoviesState(
    val idledState: Boolean = true,
    val searchQuery: String = "",
    val moviesState: UiState<Flow<PagingData<Movie>>> = UiState.Loading,
)
