package com.devhub.moviesapp.presentation.details

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.devhub.moviesapp.BuildConfig
import com.devhub.moviesapp.R
import com.devhub.moviesapp.domain.model.Movie
import com.devhub.moviesapp.presentation.details.component.ContentMovieCast
import com.devhub.moviesapp.presentation.details.component.FavoriteButton
import com.devhub.moviesapp.utils.toRatingString


@Composable
fun MovieDetailsScreen(
    movie: Movie,
    viewModel: MovieDetailsViewModel,
    onBackClick: () -> Unit,
) {


    LaunchedEffect(Unit) {
        viewModel.handleIntent(MovieDetailsIntent.CheckFavoriteMovie(movie))

        viewModel.handleIntent(MovieDetailsIntent.GetMovieCast(movie.id))
    }

    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.white)),
    ) {

        MovieDetailsContent(
            movie = movie,
            uiState = uiState,
            scrollState = scrollState,
            onBackClick = onBackClick,
            onFavoriteToggle = { isFavorite ->
                viewModel.handleIntent(
                    MovieDetailsIntent.ToggleFavorite(movie = movie, favorite = isFavorite)
                )
            })

    }
}

@Composable
fun MovieDetailsContentDeep(
    movie: Movie,
    uiState: MovieDetailsDeep,
    scrollState: ScrollState,
    onBackClick: () -> Unit,
    onFavoriteToggle: (Boolean) -> Unit,
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.outline_arrow_back_24),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 16.dp, top = 40.dp)
                    .clickable { onBackClick() })

            FavoriteButton(
                modifier = Modifier.align(Alignment.TopEnd),
                isFavorite = uiState.favoriteState,
                onClick = {
                    onFavoriteToggle(!uiState.favoriteState)
                    Toast.makeText(
                        context,
                        if (uiState.favoriteState) "Movie Removed from favorites" else "Movie Added to favorites",
                        Toast.LENGTH_SHORT
                    ).show()
                })


            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(BuildConfig.BASE_IMG_URL + movie.backdrop_path).build(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = 0.2f
            )

            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(BuildConfig.BASE_IMG_URL + movie.poster_path).build(),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp, 300.dp)
                    .clip(RoundedCornerShape(26.dp))
                    .align(Alignment.BottomCenter),
                contentScale = ContentScale.Crop,
            )

            Box(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                colorResource(R.color.black2),
                                colorResource(R.color.black1),
                            ), start = Offset(0f, 0f), end = Offset(0f, Float.POSITIVE_INFINITY)
                        )
                    )
            )

        }


        MovieDetailsBodyDeep(movie = movie, uiState = uiState)

    }
}

@Composable
fun MovieDetailsContent(
    movie: Movie,
    uiState: MovieDetailsState,
    scrollState: ScrollState,
    onBackClick: () -> Unit,
    onFavoriteToggle: (Boolean) -> Unit,
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.outline_arrow_back_24),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 16.dp, top = 40.dp)
                    .clickable { onBackClick() })

            FavoriteButton(
                modifier = Modifier.align(Alignment.TopEnd),
                isFavorite = uiState.favoriteState,
                onClick = {
                    onFavoriteToggle(!uiState.favoriteState)
                    Toast.makeText(
                        context,
                        if (uiState.favoriteState) "Movie Removed from favorites" else "Movie Added to favorites",
                        Toast.LENGTH_SHORT
                    ).show()
                })


            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(BuildConfig.BASE_IMG_URL + movie.backdrop_path).build(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = 0.2f
            )

            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(BuildConfig.BASE_IMG_URL + movie.poster_path).build(),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp, 300.dp)
                    .clip(RoundedCornerShape(26.dp))
                    .align(Alignment.BottomCenter),
                contentScale = ContentScale.Crop,
            )

            Box(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                colorResource(R.color.black2),
                                colorResource(R.color.black1),
                            ), start = Offset(0f, 0f), end = Offset(0f, Float.POSITIVE_INFINITY)
                        )
                    )
            )

        }


        MovieDetailsBody(movie = movie, uiState = uiState)

    }
}

@Composable
fun MovieDetailsBodyDeep(movie: Movie, uiState: MovieDetailsDeep) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = movie.title, style = TextStyle(
                color = colorResource(id = R.color.blue),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.cairo_bold)),
            ), modifier = Modifier.padding(top = 10.dp)
        )

        Spacer(modifier = Modifier.height(26.dp))


        Row(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Release Date", style = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                    )
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = movie.release_date, style = TextStyle(
                        color = Color.Black,
                        fontSize = 14.sp,
                    )
                )
            }

            Column(
                modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Vote Count", style = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                    )
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = movie.vote_count.toString(), style = TextStyle(
                        color = Color.Black,
                        fontSize = 14.sp,
                    )
                )
            }

            Column(
                modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Rating", style = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                    )
                )
                Spacer(modifier = Modifier.height(3.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.outline_stars_24),
                        contentDescription = null,
                        tint = colorResource(R.color.canary),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = movie.vote_average.toRatingString(), style = TextStyle(
                            color = Color.Black,
                            fontSize = 14.sp,
                        )
                    )
                }
            }
        }


        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Overview",
            style = TextStyle(
                color = colorResource(R.color.light_pink), fontSize = 24.sp
            ),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .align(Alignment.Start),
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = movie.overview, style = TextStyle(
                fontFamily = FontFamily(Font(R.font.cairo_regular)),
                color = Color.Black,
                fontSize = 14.sp
            ), modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))


        Spacer(modifier = Modifier.height(32.dp))

    }
}

@Composable
fun MovieDetailsBody(movie: Movie, uiState: MovieDetailsState) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        val context = LocalContext.current

        Image(
            painter = painterResource(R.drawable.share),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 16.dp, top = 40.dp)
                .clickable {
                    val deepLink = "https://movies.devhub.com/movie/${movie.id}"

                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, "${movie.title}\n$deepLink")
                    }

                    context.startActivity(
                        Intent.createChooser(intent, "Share movie")
                    )

                })
        Text(
            text = movie.title, style = TextStyle(
                color = colorResource(id = R.color.blue),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.cairo_bold)),
            ), modifier = Modifier.padding(top = 10.dp)
        )

        Spacer(modifier = Modifier.height(26.dp))


        Row(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Release Date", style = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                    )
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = movie.release_date, style = TextStyle(
                        color = Color.Black,
                        fontSize = 14.sp,
                    )
                )
            }

            Column(
                modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Vote Count", style = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                    )
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = movie.vote_count.toString(), style = TextStyle(
                        color = Color.Black,
                        fontSize = 14.sp,
                    )
                )
            }

            Column(
                modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Rating", style = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                    )
                )
                Spacer(modifier = Modifier.height(3.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.outline_stars_24),
                        contentDescription = null,
                        tint = colorResource(R.color.canary),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = movie.vote_average.toRatingString(), style = TextStyle(
                            color = Color.Black,
                            fontSize = 14.sp,
                        )
                    )
                }
            }
        }


        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Overview",
            style = TextStyle(
                color = colorResource(R.color.light_pink), fontSize = 24.sp
            ),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .align(Alignment.Start),
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = movie.overview, style = TextStyle(
                fontFamily = FontFamily(Font(R.font.cairo_regular)),
                color = Color.Black,
                fontSize = 14.sp
            ), modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        SectionMovieCast(uiState)

        Spacer(modifier = Modifier.height(32.dp))

    }
}

@Composable
fun SectionMovieCast(uiState: MovieDetailsState) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Cast", style = TextStyle(
                color = Color.Black,
                fontSize = 24.sp,
            ), modifier = Modifier
                .padding(horizontal = 16.dp)
                .align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(6.dp))

        ContentMovieCast(uiState)

    }

}



