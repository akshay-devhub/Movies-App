package com.devhub.moviesapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devhub.moviesapp.presentation.common.HideNavigationBarOnly
import com.devhub.moviesapp.presentation.intro.IntroScreen
import com.devhub.moviesapp.presentation.intro.SplashScreen


@Composable
fun MainNavGraph() {
    val navController = rememberNavController()

    HideNavigationBarOnly()

    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {

        composable(route = Screen.SplashScreen.route) { SplashScreen(navController) }
        composable(route = Screen.IntroScreen.route) { IntroScreen(navController) }
        composable(route = Screen.ContentNavGraph.route) { ContentNavGraph() }

    }
}
