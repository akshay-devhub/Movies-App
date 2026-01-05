package com.devhub.moviesapp.domain.usecase

import android.util.Log
import com.devhub.moviesapp.data.mapper.toMovie
import com.devhub.moviesapp.domain.model.Movie
import com.devhub.moviesapp.domain.repository.MoviesRepository
import com.devhub.moviesapp.utils.Constant.TRENDING
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTrendingMovies(
    private val moviesRepository: MoviesRepository,
) {
    operator fun invoke(): Flow<List<Movie>> {
        return moviesRepository.getTrendingMovies().map { listMovieEntity ->
            Log.i("TrendingMoviesUseCase", "getTrendingMovies: $listMovieEntity")

            listMovieEntity.map {movieEntity ->
                movieEntity.toMovie(TRENDING)  // convert MovieEntity to Movie
            }
        }
    }
}