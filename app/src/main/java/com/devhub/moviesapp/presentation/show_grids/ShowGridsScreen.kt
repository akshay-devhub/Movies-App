package com.devhub.moviesapp.presentation.show_grids

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Observer
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devhub.moviesapp.R
import com.devhub.moviesapp.domain.model.Movie
import com.devhub.moviesapp.presentation.home.HomeViewModel
import com.devhub.moviesapp.presentation.home.component.MovieSectionGridContent
import com.devhub.moviesapp.utils.Constant.NOW_PLAYING
import com.devhub.moviesapp.utils.Constant.TRENDING
import com.devhub.moviesapp.utils.NetworkViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowGridsScreen(
    title: String,
    onBackClick: () -> Unit,
    navigateToDetails: (Movie) -> Unit,
    viewModel: HomeViewModel,
    networkViewModel: NetworkViewModel

) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val title = when (title) {
        NOW_PLAYING -> stringResource(R.string.now_playing)
        TRENDING -> stringResource(R.string.trending_now)
        else -> title
    }
    var isConnected by remember { mutableStateOf(true) }
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = Observer<Boolean> { connected ->
            isConnected = connected

        }

        networkViewModel.isConnected.observe(lifecycleOwner, observer)

        onDispose {
            networkViewModel.isConnected.removeObserver(observer)
        }
    }
    if (isConnected) {
        viewModel.onInternetAvailable()
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = title) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        Box(modifier = Modifier.padding(paddingValues)) {
            if (title.contains("Trending")) {
                MovieSectionGridContent(
                    uiState = uiState.trendingMovies,
                ) { movie ->
                    navigateToDetails(movie)
                }

            } else {
                MovieSectionGridContent(
                    uiState = uiState.nowPlayingMovies,
                ) { movie ->
                    navigateToDetails(movie)
                }
            }
        }


    }

}



