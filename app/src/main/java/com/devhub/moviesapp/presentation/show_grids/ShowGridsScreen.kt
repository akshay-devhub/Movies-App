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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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



