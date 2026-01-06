package com.devhub.moviesapp.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.devhub.moviesapp.domain.usecase.MovieUseCase
import com.devhub.moviesapp.utils.Constant.NOW_PLAYING
import com.devhub.moviesapp.utils.Constant.TRENDING
import com.devhub.moviesapp.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    movieUseCase: MovieUseCase,
) : ViewModel() {

    private val trendingPagingFlow =
        movieUseCase.getMoviesByCategory(TRENDING)
            .cachedIn(viewModelScope)

    private val nowPlayingPagingFlow =
        movieUseCase.getMoviesByCategory(NOW_PLAYING)
            .cachedIn(viewModelScope)

    private val _uiState = MutableStateFlow(
        HomeState(
            trendingMovies =
                UiState.Success(trendingPagingFlow),
            nowPlayingMovies = UiState.Success(nowPlayingPagingFlow),
        )
    )
    val uiState: StateFlow<HomeState> = _uiState


}
