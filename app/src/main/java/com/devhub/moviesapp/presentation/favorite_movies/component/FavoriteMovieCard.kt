package com.devhub.moviesapp.presentation.favorite_movies.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.devhub.moviesapp.BuildConfig
import com.devhub.moviesapp.R
import com.devhub.moviesapp.domain.model.Movie
import com.devhub.moviesapp.utils.toRatingString

@Composable
fun FavoriteMovieCard(
    movie: Movie,
    onItemClick: (Movie) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    Row(
        modifier = modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(10.dp))
            .background( colorResource(id = R.color.purple_200))
            .clickable { onItemClick(movie) }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(BuildConfig.BASE_IMG_URL + movie.poster_path)
                .crossfade(true)
                .build(),
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .background(Color.Gray)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .padding(end = 8.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = movie.title,
                style = TextStyle(
                    color =  colorResource(id = R.color.white),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(2.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.outline_stars_24),
                        contentDescription = "Rating",
                        modifier = Modifier.size(12.dp)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = movie.vote_average.toRatingString(),
                        style = TextStyle(
                            color = Color.Yellow,
                            fontSize = 12.sp,
                        )
                    )
                }

                Spacer(modifier = Modifier.width(20.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "date",
                        tint = Color.Yellow,
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = movie.release_date,
                        style = TextStyle(
                            color =  colorResource(id = R.color.white),
                            fontSize = 12.sp,
                        )
                    )
                }
            }



            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = movie.overview,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 12.sp,
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )


        }
    }
}
