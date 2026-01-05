package com.devhub.moviesapp.domain.usecase

import com.devhub.moviesapp.data.mapper.toMovie
import com.devhub.moviesapp.domain.model.Movie
import com.devhub.moviesapp.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetFavoriteMovies(
    private val moviesRepository: MoviesRepository,
) {
    operator fun invoke(): Flow<List<Movie>> =
        moviesRepository.getAllFavoriteMovies().map { listFavoriteMovieEntity ->
            listFavoriteMovieEntity.map { favoriteMovieEntity ->
                favoriteMovieEntity.toMovie()
            }
        }
}