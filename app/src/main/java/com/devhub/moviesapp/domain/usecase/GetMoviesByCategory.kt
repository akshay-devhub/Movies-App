package com.devhub.moviesapp.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.devhub.moviesapp.data.mapper.toMovie
import com.devhub.moviesapp.domain.model.Movie
import com.devhub.moviesapp.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class GetMoviesByCategory(
    private val moviesRepository: MoviesRepository,
) {
    operator fun invoke(category: String): Flow<PagingData<Movie>> {
        return moviesRepository.getMoviesListByCategory(category).map { pagingData ->
            pagingData.map { movieEntity ->
                movieEntity.toMovie(category)  // convert MovieEntity to Movie
            }
        }
    }
}