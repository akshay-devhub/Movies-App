package com.devhub.moviesapp.presentation.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import com.devhub.moviesapp.domain.model.Movie
import com.devhub.moviesapp.presentation.common.MovieCard
import com.devhub.moviesapp.utils.UiState
import imageShimmer
import kotlinx.coroutines.flow.Flow


@Composable
fun MovieSectionContent(
    uiState: UiState<Flow<PagingData<Movie>>>,
    onClick: (Movie) -> Unit,
) {
    when (uiState) {
        is UiState.Loading -> {
            Loader(50.dp)
        }

        is UiState.Success -> {
            uiState.data?.let { pagingFlow ->
                val lazyPagingItems = pagingFlow.collectAsLazyPagingItems()

                ListMoviesContent(
                    moviesItems = lazyPagingItems,
                    onClick = onClick
                )
            }
        }

        is UiState.Error -> {
            Text(
                text = "Error: ${uiState.message}",
                color = Color.Red
            )
        }
    }
}

@Composable
fun ListMoviesContent(
    moviesItems: LazyPagingItems<Movie>,
    onClick: (Movie) -> Unit,
) {
    if (moviesItems.loadState.refresh is LoadState.Loading) {
        Loader(50.dp)
        return // ✅ IMPORTANT
    }

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(
            count = moviesItems.itemCount,
            key = { index ->
                moviesItems[index]?.id ?: "row_placeholder_$index"
            }
        ) { index ->
            val movie = moviesItems[index]
            if (movie != null) {
                MovieCard(
                    movie = movie,
                    onClick = { onClick(movie) }
                )
            }
        }

        item {
            if (moviesItems.loadState.append is LoadState.Loading) {
                CircularProgressIndicator()
            }
        }
    }
}


@Composable
fun MovieSectionGridContent(
    uiState: UiState<Flow<PagingData<Movie>>>,
    onClick: (Movie) -> Unit,
) {
    when (uiState) {
        is UiState.Loading -> {
            Loader(120.dp)
        }

        is UiState.Success -> {
            uiState.data?.let { pagingFlow ->
                val lazyPagingItems = pagingFlow.collectAsLazyPagingItems()

                GridMoviesContent(
                    moviesItems = lazyPagingItems,
                    onClick = onClick
                )
            }
        }

        is UiState.Error -> {
            Text(
                text = "Error: ${uiState.message}",
                color = Color.Red
            )
        }
    }
}

@Composable
fun GridMoviesContent(
    moviesItems: LazyPagingItems<Movie>,
    onClick: (Movie) -> Unit,
) {
    if (moviesItems.loadState.refresh is LoadState.Loading) {
        Loader(120.dp)
        return
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 8.dp
        )
    ) {
        items(
            count = moviesItems.itemCount,
            key = { index ->
                moviesItems[index]?.id ?: "grid_placeholder_$index"
            }
        ) { index ->
            val movie = moviesItems[index]
            if (movie != null) {
                MovieCard(
                    movie = movie,
                    onClick = { onClick(movie) }
                )
            }
        }

        item {
            if (moviesItems.loadState.append is LoadState.Loading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}


@Composable
private fun Loader(height: androidx.compose.ui.unit.Dp) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ImageShimmerBox() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .imageShimmer()
    )
}

@Composable
fun MoviePoster(
    imageUrl: String,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp) // ✅ FIXED HEIGHT (MANDATORY)
    ) {
        SubcomposeAsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            loading = {
                ImageShimmerBox() // ✅ SHIMMER VISIBLE
            },
            error = {
                ImageShimmerBox() // or error image
            }
        )
    }
}


