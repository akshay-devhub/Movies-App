package com.devhub.moviesapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.devhub.moviesapp.presentation.home.HomeViewModel
import com.devhub.moviesapp.presentation.navigation.MainNavGraph
import com.devhub.moviesapp.ui.theme.MoviesAppTheme
import com.devhub.moviesapp.utils.NetworkObserver
import com.devhub.moviesapp.utils.NetworkViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.devhub.moviesapp.R

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val blueColor = ContextCompat.getColor(this, R.color.blue)
        val blueDarkColor = ContextCompat.getColor(this, R.color.purple_700)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                blueColor,
                darkScrim = blueDarkColor
            )
        )
        val networkViewModel: NetworkViewModel by viewModels()
        val homeViewModel: HomeViewModel by viewModels()


        setContent {
            NetworkObserver(networkViewModel, homeViewModel) {
                MoviesAppTheme {
                    MainNavGraph()
                }
            }

        }
    }
}
