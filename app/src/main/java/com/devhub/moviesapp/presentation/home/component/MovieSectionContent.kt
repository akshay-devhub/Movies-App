package com.devhub.moviesapp.presentation.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import com.devhub.moviesapp.R
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

        is UiState.Error -> {
            RetryView(
                message = uiState.message, onRetry = {})
        }

        is UiState.Success -> {
            val moviesItems = uiState.data?.collectAsLazyPagingItems()

            val hasData = moviesItems?.itemCount!! > 0
            val refreshState = moviesItems?.loadState?.refresh

            var showRetry by remember { mutableStateOf(false) }
            var errorMessage by remember { mutableStateOf<String?>(null) }

            LaunchedEffect(refreshState) {
                if (refreshState is LoadState.Error && !hasData) {
                    showRetry = true
                    errorMessage = refreshState.error.message
                }

                if (refreshState is LoadState.Loading) {
                }
            }

            when {
                refreshState is LoadState.Loading && !hasData && !showRetry -> {
                    Loader(50.dp)
                }

                showRetry && !hasData -> {
                    RetryView(
                        message = errorMessage ?: "Something went wrong", onRetry = {
                            showRetry = false
                            moviesItems.refresh()
                        })
                }

                else -> {
                    ListMoviesContent(
                        moviesItems = moviesItems, onClick = onClick
                    )
                }
            }
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
        return
    }

    val listState = rememberLazyListState()


    LazyRow(
        state = listState,
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {

        items(
            count = moviesItems.itemCount, key = { index ->
                moviesItems[index]?.id ?: "row_placeholder_$index"
            }) { index ->
            val movie = moviesItems[index]
            if (movie != null) {
                MovieCard(
                    movie = movie, onClick = { onClick(movie) })
            }
        }


        item {
            if (moviesItems.loadState.append is LoadState.Loading) {
                CircularProgressIndicator()
            }
        }
    }
    LaunchedEffect(moviesItems.itemCount) {
        if (moviesItems.itemCount > 0) {
            listState.scrollToItem(0)
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
                    moviesItems = lazyPagingItems, onClick = onClick
                )
            }
        }

        is UiState.Error -> {
            Text(
                text = "Error: ${uiState.message}", color = Color.Red
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
            horizontal = 16.dp, vertical = 8.dp
        )
    ) {
        items(
            count = moviesItems.itemCount, key = { index ->
                moviesItems[index]?.id ?: "grid_placeholder_$index"
            }) { index ->
            val movie = moviesItems[index]
            if (movie != null) {
                MovieCard(
                    movie = movie, onClick = { onClick(movie) })
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
            .height(height), contentAlignment = Alignment.Center
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
            .height(220.dp)
    ) {
        SubcomposeAsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            loading = {
                ImageShimmerBox()
            },
            error = {
                ImageShimmerBox()
            })
    }
}

@Composable
fun RetryView(
    message: String, onRetry: () -> Unit
) {
    var errorMSg = "";
    if (message.contains("No address")) {
        errorMSg = stringResource(id = R.string.waiting_net_check)
    }
    Column(
        modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = errorMSg)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}


