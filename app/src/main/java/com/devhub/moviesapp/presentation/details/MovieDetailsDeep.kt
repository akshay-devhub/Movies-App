package com.devhub.moviesapp.presentation.details

import com.devhub.moviesapp.domain.model.Movie
import com.google.android.gms.cast.Cast

data class MovieDetailsDeep(
    val movie: Movie? = null,
    val cast: List<Cast> = emptyList(),
    val favoriteState: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)
