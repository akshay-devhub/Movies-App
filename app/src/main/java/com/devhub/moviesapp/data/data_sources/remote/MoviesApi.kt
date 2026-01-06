package com.devhub.moviesapp.data.data_sources.remote

import com.devhub.moviesapp.BuildConfig
import com.devhub.moviesapp.data.data_sources.remote.dto.movie_cast.MovieCastResponse
import com.devhub.moviesapp.data.data_sources.remote.dto.movie.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("trending/movie/week")
    suspend fun getTrendingMovies(
        @Query("page") page: Int = 0,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): MoviesResponse

    @GET("movie/{category}")
    suspend fun getMoviesList(
        @Path("category") category: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): MoviesResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCast(
        @Path("movie_id") movieId: Long,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): MovieCastResponse


    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): MoviesResponse

//deep link movie search
    @GET("find/{imdb_id}")
    suspend fun findMovieByImdbId(
        @Path("imdb_id") imdbId: String,
        @Query("external_source") source: String = "imdb_id",
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): MoviesResponse

}