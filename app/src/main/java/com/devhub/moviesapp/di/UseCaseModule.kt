package com.devhub.moviesapp.di


import com.devhub.moviesapp.domain.repository.MoviesRepository
import com.devhub.moviesapp.domain.usecase.GetFavoriteMovies
import com.devhub.moviesapp.domain.usecase.GetMovieCast
import com.devhub.moviesapp.domain.usecase.GetMoviesByCategory
import com.devhub.moviesapp.domain.usecase.GetTrendingMovies
import com.devhub.moviesapp.domain.usecase.MovieUseCase
import com.devhub.moviesapp.domain.usecase.SearchMovies
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideUseCase(
        moviesRepository: MoviesRepository,
    ): MovieUseCase {
        return MovieUseCase(
            getTrendingMovies = GetTrendingMovies(moviesRepository),
            getMoviesByCategory = GetMoviesByCategory(moviesRepository),
            getMovieCast = GetMovieCast(moviesRepository),
            searchMovies = SearchMovies(moviesRepository),
            getFavoriteMovies = GetFavoriteMovies(moviesRepository),
        )
    }

}