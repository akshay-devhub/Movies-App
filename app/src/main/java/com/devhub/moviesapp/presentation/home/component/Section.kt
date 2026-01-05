package com.devhub.moviesapp.presentation.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devhub.moviesapp.R

@Composable
inline fun Section(title: String, content: @Composable () -> Unit) {
    Column {


        Text(
            text = title,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.black)
            ),
            fontFamily = FontFamily(Font(R.font.cairo_regular)),
            modifier = Modifier.padding(start = 16.dp, top = 30.dp)
        )

        HorizontalDivider(
            modifier = Modifier
                .width(160.dp)
                .padding(start = 10.dp),
            thickness = 1.dp,
            color = colorResource(R.color.blue),

            )
        Spacer(modifier = Modifier.height(22.dp))

        content()
    }
}