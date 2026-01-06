package com.devhub.moviesapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.room.withTransaction
import com.devhub.moviesapp.data.data_sources.local.FavoriteMovieEntity
import com.devhub.moviesapp.data.data_sources.local.MovieCastEntity
import com.devhub.moviesapp.data.data_sources.local.MovieDatabase
import com.devhub.moviesapp.data.data_sources.local.MovieEntity
import com.devhub.moviesapp.data.data_sources.remote.MoviesApi
import com.devhub.moviesapp.data.data_sources.remote.dto.movie.MovieDto
import com.devhub.moviesapp.data.mapper.toFavoriteMovieEntity
import com.devhub.moviesapp.data.mapper.toMovie
import com.devhub.moviesapp.data.mapper.toMovieCastEntity
import com.devhub.moviesapp.data.mapper.toMovieEntity
import com.devhub.moviesapp.data.paging.MovieSearchPagingSource
import com.devhub.moviesapp.data.paging.NowPlayingMoviesRemoteMediator
import com.devhub.moviesapp.data.paging.TrendingMoviesRemoteMediator
import com.devhub.moviesapp.domain.model.Movie
import com.devhub.moviesapp.domain.repository.MoviesRepository
import com.devhub.moviesapp.utils.Constant.NOW_PLAYING
import com.devhub.moviesapp.utils.Constant.TRENDING
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow

@ExperimentalPagingApi
class MoviesRepositoryImpl(
    private val moviesApi: MoviesApi,
    private val movieDatabase: MovieDatabase,
) : MoviesRepository {

    override fun getTrendingMovies(): Flow<List<MovieEntity>> = channelFlow {
        val trendingMovies = moviesApi.getTrendingMovies(1).results

        movieDatabase.withTransaction {

            val listMovieEntity = trendingMovies.map { movieDto ->
                movieDto.toMovieEntity(TRENDING)
            }

            // insert trending movies to database
            movieDatabase.movieDao.upsertMoviesList(movies = listMovieEntity)

            val listTrendingMovies = movieDatabase.movieDao.getTrendingMovies()

            send(listTrendingMovies)
        }
    }


    override fun getMoviesListByCategory(
        category: String,
    ): Flow<PagingData<MovieEntity>> {
        val pagingSourceFactory = { movieDatabase.movieDao.getMoviesListByCategory(category) }

        val mediator = when (category) {
            TRENDING -> TrendingMoviesRemoteMediator(moviesApi, movieDatabase)
            NOW_PLAYING -> NowPlayingMoviesRemoteMediator(moviesApi, movieDatabase)
            else -> throw IllegalArgumentException("Unknown category: $category")
        }

        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = mediator,
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }


    override fun getMovieCast(movieId: Long): Flow<List<MovieCastEntity>> = channelFlow {
        val movieCast = moviesApi.getMovieCast(movieId).movieCastDto

        movieDatabase.withTransaction {

            val listMovieCastEntity = movieCast.map { movieCastDto ->
                movieCastDto.toMovieCastEntity(movieId)
            }

            // insert movies cast to database
            movieDatabase.movieDao.upsertMovieCastList(movieCast = listMovieCastEntity)

            val listMovieCast = movieDatabase.movieDao.getMovieCastById(movieId)

            send(listMovieCast)
        }
    }


    override fun searchMovies(query: String): Flow<PagingData<MovieDto>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                MovieSearchPagingSource(moviesApi, query)
            }
        ).flow
    }

    override suspend fun addFavoriteMovie(movie: Movie) {
        movieDatabase.movieDao.upsertFavoriteMovie(movie.toFavoriteMovieEntity())
    }

    override suspend fun getFavoriteMovieById(movieId: Long): Movie? {
        return movieDatabase.movieDao.getFavoriteMovieById(movieId)?.toMovie()
    }

    override suspend fun deleteFavoriteMovie(movieId: Long) {
        movieDatabase.movieDao.deleteFavoriteMovie(movieId)
    }

    override fun getAllFavoriteMovies(): Flow<List<FavoriteMovieEntity>> {
        return flow {
            emit(movieDatabase.movieDao.getAllFavoriteMovies())
        }
    }

    override suspend fun getMovieByImdbId(imdbId: String): Movie {
        val response = moviesApi.findMovieByImdbId(
            imdbId = imdbId,
            source = "imdb_id"
        )
        return response.results.get(0).toMovie()
    }

}
