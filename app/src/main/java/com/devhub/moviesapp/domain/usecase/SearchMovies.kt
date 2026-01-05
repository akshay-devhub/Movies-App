package com.devhub.moviesapp.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.devhub.moviesapp.data.mapper.toMovie
import com.devhub.moviesapp.domain.model.Movie
import com.devhub.moviesapp.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchMovies(
    private val moviesRepository: MoviesRepository,
) {
    operator fun invoke(query: String): Flow<PagingData<Movie>> {
        return moviesRepository.searchMovies(query).map { pagingData ->
            pagingData.map { movieDto ->
                movieDto.toMovie() // Assuming you have a mapper function
            }
        }
    }
}