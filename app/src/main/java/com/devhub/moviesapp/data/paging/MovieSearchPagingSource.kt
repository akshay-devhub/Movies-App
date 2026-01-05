package com.devhub.moviesapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.devhub.moviesapp.data.data_sources.remote.MoviesApi
import com.devhub.moviesapp.data.data_sources.remote.dto.movie.MovieDto

class MovieSearchPagingSource(
    private val moviesApi: MoviesApi,
    private val query: String,
) : PagingSource<Int, MovieDto>() {

    private var totalMovieCount = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDto> {
        val page = params.key ?: 1
        return try {
            val movieResponse = moviesApi.searchMovies(query, page)

            totalMovieCount += movieResponse.results.size

            LoadResult.Page(
                data = movieResponse.results,
                nextKey = if (totalMovieCount == movieResponse.total_results) null else page + 1,
                prevKey = null,
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(throwable = e)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, MovieDto>): Int? {
        return state.anchorPosition?.let { anchorPage ->
            val page = state.closestPageToPosition(anchorPage)
            page?.nextKey?.minus(1) ?: page?.prevKey?.plus(1)
        }
    }

}