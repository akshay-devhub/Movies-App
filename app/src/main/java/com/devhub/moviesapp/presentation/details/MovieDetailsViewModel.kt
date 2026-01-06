package com.devhub.moviesapp.presentation.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhub.moviesapp.domain.model.Movie
import com.devhub.moviesapp.domain.repository.MoviesRepository
import com.devhub.moviesapp.domain.usecase.MovieUseCase
import com.devhub.moviesapp.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase,
    private val moviesRepository: MoviesRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailsState())
    private val _uiStateDeep = MutableStateFlow(MovieDetailsDeep())

    val uiState: StateFlow<MovieDetailsState> = _uiState
    val uiStateDeep: StateFlow<MovieDetailsDeep> = _uiStateDeep


    fun handleIntent(intent: MovieDetailsIntent) {
        when (intent) {
            is MovieDetailsIntent.CheckFavoriteMovie -> checkFavoriteMovie(intent.movie)
            is MovieDetailsIntent.ToggleFavorite -> toggleFavorite(intent.movie, intent.favorite)
            is MovieDetailsIntent.GetMovieCast -> fetchMovieCast(intent.movieId)
        }
    }


    private fun fetchMovieCast(movieId: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(movieCastState = UiState.Loading) }
            try {
                movieUseCase.getMovieCast(movieId).collect { listMovieCast ->
                    _uiState.update {
                        it.copy(movieCastState = UiState.Success(listMovieCast))
                    }
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        movieCastState = UiState.Error(
                            e.message ?: "An error occurred while loading the movie cast"
                        )
                    )
                }
            }
        }
    }


    private fun checkFavoriteMovie(movie: Movie) {
        viewModelScope.launch {
            try {
                val returnMovie = moviesRepository.getFavoriteMovieById(movie.id)

                if (returnMovie != null) {
                    _uiState.update { it.copy(favoriteState = true) }
                } else {
                    _uiState.update { it.copy(favoriteState = false) }
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        movieState = UiState.Error(
                            e.message ?: "An error occurred while check favorite movie"
                        )
                    )
                }
            }
        }
    }

    private fun toggleFavorite(movie: Movie, favorite: Boolean) {
        viewModelScope.launch {
            try {

                if (favorite) {
                    moviesRepository.addFavoriteMovie(movie)
                    _uiState.update { it.copy(favoriteState = true) }
                } else {
                    moviesRepository.deleteFavoriteMovie(movie.id)
                    _uiState.update { it.copy(favoriteState = false) }
                }


            } catch (e: Exception) {
                Log.e("DetailsViewModel", "toggleFavorite: ${e.message}")
            }
        }
    }

    fun loadMovieByImdbId(imdbId: String) {
        viewModelScope.launch {
            try {

                _uiStateDeep.update { it.copy(isLoading = true, error = null) }

                val movie = moviesRepository.getMovieByImdbId(imdbId)

                _uiStateDeep.update {
                    it.copy(
                        movie = movie,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiStateDeep.update {
                    it.copy(
                        error = e.message ?: "Something went wrong",
                        isLoading = false
                    )
                }
            }
        }
    }


}