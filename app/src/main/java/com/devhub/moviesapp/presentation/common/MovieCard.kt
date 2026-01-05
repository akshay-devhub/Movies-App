package com.devhub.moviesapp.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.devhub.moviesapp.BuildConfig
import com.devhub.moviesapp.R
import com.devhub.moviesapp.domain.model.Movie
import com.devhub.moviesapp.utils.toRatingString


@Composable
fun MovieCard(movie: Movie, onClick: (Movie) -> Unit) {
    Column(
        modifier = Modifier
            .padding(4.dp)
            .width(130.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick(movie) }
            .background(
                color = colorResource(R.color.white)
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current

        // this like a glide image loader
        AsyncImage(
            model = ImageRequest.Builder(context).data(BuildConfig.BASE_IMG_URL + movie.poster_path)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(colorResource(R.color.white)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = movie.title,
            modifier = Modifier.padding(start = 4.dp),
            style = TextStyle(
                color = colorResource(R.color.black),
                fontSize = 12.sp,
            ),
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 4.dp, bottom = 4.dp),
        ) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = colorResource(R.color.canary),
                modifier = Modifier.size(16.dp)
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = movie.vote_average.toRatingString(),
                style = TextStyle(
                    color = colorResource(R.color.canary),
                    fontSize = 12.sp,
                ),
                maxLines = 1
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

    }
}



