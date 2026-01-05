package com.devhub.moviesapp.presentation.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.devhub.moviesapp.domain.model.Movie
import com.devhub.moviesapp.presentation.common.MovieCard
import com.devhub.moviesapp.utils.UiState
import kotlinx.coroutines.flow.Flow


@Composable
fun MovieSectionContent(
    uiState: UiState<Flow<PagingData<Movie>>>,
    onClick: (Movie) -> Unit,
) {
    when (uiState) {
        is UiState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is UiState.Success -> uiState.data?.let { pagingData ->
            val lazyPagingItems = remember { pagingData }.collectAsLazyPagingItems()
            ListMoviesContent( lazyPagingItems, onClick = onClick)
        }

        is UiState.Error -> Text("Errors: ${uiState.message}", color = Color.Red)
    }
}


@Composable
fun ListMoviesContent(
    moviesItems: LazyPagingItems<Movie>,
    onClick: (Movie) -> Unit,
) {
    if (moviesItems.loadState.refresh is LoadState.Loading) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(
            count = moviesItems.itemCount,
        ) { index ->
            val movie = moviesItems[index]
            if (movie != null) {

                    MovieCard(movie = movie, onClick = { onClick(movie) })


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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is UiState.Success -> uiState.data?.let { pagingData ->
            val lazyPagingItems = pagingData.collectAsLazyPagingItems()
            GridMoviesContent(
                moviesItems = lazyPagingItems,
                onClick = onClick
            )
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
    onClick: ( Movie) -> Unit,
) {
    if (moviesItems.loadState.refresh is LoadState.Loading) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    androidx.compose.foundation.lazy.grid.LazyVerticalGrid(
        columns = androidx.compose.foundation.lazy.grid.GridCells.Fixed(2), // 👈 2 columns
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 8.dp
        )
    ) {

        items(
            count = moviesItems.itemCount
        ) { index ->
            val movie = moviesItems[index]
            if (movie != null) {
                MovieCard(
                    movie = movie,
                    onClick = { onClick(movie) }
                )
            }
        }

        // Paging append loader
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
