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
    private val movieUseCase: MovieUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState

    private var isHomeDataLoaded = false

    init {
        loadHomeDataOnce()
    }

    fun onInternetAvailable() {
        loadDataRetry()
    }


    fun loadHomeDataOnce() {
        if (isHomeDataLoaded) return
        isHomeDataLoaded = true

        getTrendingMovies()
        getNowPlayingMovies()
    }

    fun loadDataRetry() {
        getTrendingMovies(forceReload = true)
        getNowPlayingMovies(forceReload = true)
    }


    private fun getTrendingMovies(forceReload: Boolean = false) {
        if (!forceReload && _uiState.value.trendingMovies is UiState.Success) return

        viewModelScope.launch {
            _uiState.update { it.copy(trendingMovies = UiState.Loading) }
            try {
                val data = movieUseCase
                    .getMoviesByCategory(TRENDING)
                    .cachedIn(viewModelScope)

                _uiState.update {
                    it.copy(trendingMovies = UiState.Success(data))
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(trendingMovies = UiState.Error(e.message.orEmpty()))
                }
            }
        }
    }

    private fun getNowPlayingMovies(forceReload: Boolean = false) {
        if (!forceReload && _uiState.value.nowPlayingMovies is UiState.Success) return

        viewModelScope.launch {
            _uiState.update { it.copy(nowPlayingMovies = UiState.Loading) }
            try {
                val data = movieUseCase.getMoviesByCategory(NOW_PLAYING).cachedIn(viewModelScope)
                _uiState.update {
                    it.copy(nowPlayingMovies = UiState.Success(data))
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(nowPlayingMovies = UiState.Error(e.message.orEmpty()))
                }
            }
        }
    }
}
