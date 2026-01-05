package com.devhub.moviesapp.domain.usecase

import com.devhub.moviesapp.data.mapper.toMovieCast
import com.devhub.moviesapp.domain.model.MovieCast
import com.devhub.moviesapp.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetMovieCast(
    private val moviesRepository: MoviesRepository,
) {
    operator fun invoke(movieId: Int): Flow<List<MovieCast>> {
        val listMovieCast = moviesRepository.getMovieCast(movieId).map { listMovieCastEntity ->
            listMovieCastEntity.map {
                it.toMovieCast()
            }
        }
        return listMovieCast
    }
}