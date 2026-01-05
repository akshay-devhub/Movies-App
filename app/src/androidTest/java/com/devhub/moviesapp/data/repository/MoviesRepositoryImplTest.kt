package com.devhub.moviesapp.data.repository

import androidx.paging.ExperimentalPagingApi
import app.cash.turbine.test
import com.devhub.moviesapp.data.FakeMovie1
import com.devhub.moviesapp.data.FakeMovie2
import com.devhub.moviesapp.data.InMemoryDatabaseRule
import com.devhub.moviesapp.data.data_sources.remote.MoviesApi
import com.devhub.moviesapp.data.data_sources.remote.dto.movie.MoviesResponse
import com.devhub.moviesapp.data.mapper.toMovieEntity
import com.devhub.moviesapp.utils.Constant.TRENDING
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalPagingApi::class)
class MoviesRepositoryImplTest {

    private val moviesApi: MoviesApi = mockk()
    private lateinit var repository: MoviesRepositoryImpl

    @get:Rule
    val inMemoryDatabaseRule = InMemoryDatabaseRule()


    @Before
    fun setUp() {
        repository = MoviesRepositoryImpl(moviesApi, inMemoryDatabaseRule.database)
    }


    @Test
    fun getTrendingMovies_emits_data_from_database_after_inserting() = runTest {
        val movieDtoList = listOf(
            FakeMovie1,
            FakeMovie2
        )

        // fake movie response
        val movieResponse = MoviesResponse(
            page = 1,
            results = movieDtoList,
            total_pages = 2,
            total_results = 1
        )

        // Mock API
        coEvery { moviesApi.getTrendingMovies(1) } returns movieResponse

        val movieEntityList = movieDtoList.map { it.toMovieEntity(TRENDING) }

        repository.getTrendingMovies().test {
            val result = awaitItem()

            assertEquals(2,  result.size)

            assertEquals(movieEntityList, result)

            assertEquals("The Movie Fake 1 Adventure", result.get(0).title)

            cancelAndConsumeRemainingEvents()
        }

    }

}