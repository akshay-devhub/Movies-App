package com.devhub.moviesapp.presentation.details.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.devhub.moviesapp.R

@Composable
fun FavoriteButton(modifier: Modifier, isFavorite: Boolean, onClick: () -> Unit) {
    Icon(
        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
        contentDescription = null,
        tint =colorResource(R.color.light_red),
        modifier = modifier
            .padding(end = 16.dp, top = 40.dp)
            .size(36.dp)
            .clickable { onClick() }
    )
}
