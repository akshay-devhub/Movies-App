package com.devhub.moviesapp.domain.repository

import androidx.paging.PagingData
import com.devhub.moviesapp.data.data_sources.local.FavoriteMovieEntity
import com.devhub.moviesapp.data.data_sources.local.MovieCastEntity
import com.devhub.moviesapp.data.data_sources.local.MovieEntity
import com.devhub.moviesapp.data.data_sources.remote.dto.movie.MovieDto
import com.devhub.moviesapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    fun getTrendingMovies(): Flow<List<MovieEntity>>

    fun getMoviesListByCategory(
        category: String,
    ): Flow<PagingData<MovieEntity>>


    fun getMovieCast(movieId: Long): Flow<List<MovieCastEntity>>

    fun searchMovies(query: String): Flow<PagingData<MovieDto>>


    suspend fun addFavoriteMovie(movie: Movie)

    suspend fun getFavoriteMovieById(movieId: Long): Movie?

    suspend fun deleteFavoriteMovie(movieId: Long)

    fun getAllFavoriteMovies(): Flow<List<FavoriteMovieEntity>>
    suspend fun getMovieByImdbId(imdbId: String): Movie

}