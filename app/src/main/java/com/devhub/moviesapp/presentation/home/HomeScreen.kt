package com.devhub.moviesapp.presentation.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRightAlt
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.devhub.moviesapp.R
import com.devhub.moviesapp.domain.model.Movie
import com.devhub.moviesapp.presentation.common.SearchBar
import com.devhub.moviesapp.presentation.home.component.MovieSectionContent
import com.devhub.moviesapp.presentation.home.component.Section
import com.devhub.moviesapp.utils.Constant.NOW_PLAYING
import com.devhub.moviesapp.utils.Constant.TRENDING
import com.devhub.moviesapp.utils.NetworkViewModel
import com.devhub.moviesapp.utils.UiState
import kotlinx.coroutines.launch


@SuppressLint("LocalContextGetResourceValueCall")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onClickSearchBar: () -> Unit,
    navigateToDetails: (Movie) -> Unit,
    navigateToGrid: (String) -> Unit,
    networkViewModel: NetworkViewModel

) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val lifecycleOwner = LocalLifecycleOwner.current
    var isConnected by remember { mutableStateOf(true) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var totalLoadedCached: Int? = 0

    val pagingItems = (uiState.trendingMovies as UiState.Success)
        .data
        ?.collectAsLazyPagingItems()
    totalLoadedCached = pagingItems?.itemCount

    DisposableEffect(lifecycleOwner) {
        val observer = Observer<Boolean> { connected ->
            isConnected = connected

        }

        networkViewModel.isConnected.observe(lifecycleOwner, observer)

        onDispose {
            networkViewModel.isConnected.removeObserver(observer)
        }
    }
    val context = LocalContext.current

    LaunchedEffect(isConnected) {
        if (!isConnected) {
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = context.getString(R.string.no_net_check)
                )
            }

        }
    }



    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = TextStyle(
                            color = colorResource(R.color.light_pink),
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(Font(R.font.cairo_bold))
                        )
                    )
                },

                )

        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)

        ) {
            if (totalLoadedCached == 0 || !isConnected) {
                RetryView(
                    message = stringResource(id = R.string.no_net_check),
                    onRetry = {
                        if (isConnected) {
                            viewModel.loadDataRetry()

                        }
                    }
                )
            } else{
                Column {  }
            }
            Text(
                text = stringResource(id = R.string.header_text),
                style = TextStyle(
                    color = colorResource(R.color.black),
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    fontFamily = FontFamily(Font(R.font.cairo_regular)),
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )


            SearchBar(
                isClickable = true, onclick = { onClickSearchBar() })



            Section(title = stringResource(R.string.trending_now)) {
                MovieSectionContent(uiState.trendingMovies) { movie ->
                    navigateToDetails(movie)
                }

            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                horizontalAlignment = Alignment.End
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowRightAlt,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.White)
                        .size(28.dp)
                        .clickable {
                            navigateToGrid(TRENDING)
                        }
                )
            }
            Section(title = stringResource(R.string.now_playing)) {
                MovieSectionContent(uiState.nowPlayingMovies) { movie ->
                    navigateToDetails(movie)
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                horizontalAlignment = Alignment.End
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowRightAlt,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.White)
                        .size(28.dp)
                        .clickable {
                            navigateToGrid(NOW_PLAYING)
                        }
                )
            }

        }
    }
}

@Composable
fun RetryView(
    modifier: Modifier = Modifier,
    message: String = "Something went wrong",
    onRetry: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = "Retry",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(40.dp)
                .clickable { onRetry() }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = "Retry",
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 6.dp)
                .clickable { onRetry() }
        )
    }
}



