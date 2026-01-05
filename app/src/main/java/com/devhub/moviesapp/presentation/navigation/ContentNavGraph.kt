package com.devhub.moviesapp.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.devhub.moviesapp.R
import com.devhub.moviesapp.domain.model.Movie
import com.devhub.moviesapp.presentation.bottom_navigation.BottomBar
import com.devhub.moviesapp.presentation.bottom_navigation.BottomNavItem
import com.devhub.moviesapp.presentation.common.HideNavigationBarOnly
import com.devhub.moviesapp.presentation.deeplinks.MovieScreenDL
import com.devhub.moviesapp.presentation.details.MovieDetailsScreen
import com.devhub.moviesapp.presentation.details.MovieDetailsViewModel
import com.devhub.moviesapp.presentation.favorite_movies.FavoriteMovieViewModel
import com.devhub.moviesapp.presentation.favorite_movies.FavoriteMoviesScreen
import com.devhub.moviesapp.presentation.home.HomeScreen
import com.devhub.moviesapp.presentation.home.HomeViewModel
import com.devhub.moviesapp.presentation.search.SearchMoviesViewModel
import com.devhub.moviesapp.presentation.search.SearchScreen
import com.devhub.moviesapp.presentation.show_grids.ShowGridsScreen
import com.devhub.moviesapp.utils.NetworkViewModel

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun ContentNavGraph() {
    val bottomNavigationItems = remember {
        listOf(
            BottomNavItem(Icons.Default.Home, "Home"),
            BottomNavItem(Icons.Default.Search, "Search"),
            BottomNavItem(Icons.Default.Favorite, "Favorite")
        )
    }


    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable { mutableStateOf(0) }

    selectedItem = when (backStackState?.destination?.route) {
        Screen.HomeScreen.route -> 0
        Screen.SearchScreen.route -> 1
        Screen.FavoriteMoviesScreen.route -> 2
        else -> 0
    }


    //Hide the bottom navigation when the user is in the details screen
    val isBottomBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == Screen.HomeScreen.route ||
                backStackState?.destination?.route == Screen.SearchScreen.route ||
                backStackState?.destination?.route == Screen.FavoriteMoviesScreen.route
    }


    // Hide navigation bar
    HideNavigationBarOnly()


    Scaffold(
        bottomBar = {
            if (isBottomBarVisible) {
                BottomBar(
                    items = bottomNavigationItems,
                    selectedItem = selectedItem,
                    onItemClick = { index ->
                        when (index) {
                            0 -> navigateToTab(
                                navController = navController,
                                route = Screen.HomeScreen.route
                            )

                            1 -> navigateToTab(
                                navController = navController,
                                route = Screen.SearchScreen.route
                            )

                            2 -> navigateToTab(
                                navController = navController,
                                route = Screen.FavoriteMoviesScreen.route
                            )
                        }
                    }
                )

            }
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.white))
                .padding(bottom = innerPadding.calculateBottomPadding())
        ) {


            NavHost(
                navController = navController,
                startDestination = Screen.HomeScreen.route,
            ) {
                composable(route = Screen.HomeScreen.route) { backStackEntry ->

                    val parentEntry = remember(backStackEntry) {
                        navController.getBackStackEntry(Screen.HomeScreen.route)
                    }

                    val homeViewModel = hiltViewModel<HomeViewModel>(parentEntry)
                    val networkViewModel: NetworkViewModel =
                        hiltViewModel(navController.getBackStackEntry(Screen.HomeScreen.route))

                    HomeScreen(
                        viewModel = homeViewModel,
                        onClickSearchBar = {
                            selectedItem = 1
                            navController.navigate(Screen.SearchScreen.route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        navigateToDetails = { movie ->
                            navigateToDetails(
                                navController = navController,
                                movie = movie
                            )
                        },
                        navigateToGrid = { showType ->
                            navController.navigate(
                                Screen.GridScreen.createRoute(showType)
                            ) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        networkViewModel = networkViewModel
                    )
                }

                composable(route = Screen.SearchScreen.route) {
                    val viewModel: SearchMoviesViewModel = hiltViewModel()
                    SearchScreen(
                        viewModel = viewModel,
                        navigateToDetails = { movie ->
                            navigateToDetails(
                                navController = navController,
                                movie = movie
                            )
                        }
                    )
                }
                composable(route = Screen.FavoriteMoviesScreen.route) {
                    val viewModel: FavoriteMovieViewModel = hiltViewModel()
                    FavoriteMoviesScreen(
                        viewModel, navigateToDetails = { movie ->
                            navigateToDetails(
                                navController = navController,
                                movie = movie
                            )
                        }
                    )
                }

                composable(route = Screen.DetailsScreen.route) { backStackEntry ->
                    val viewModel: MovieDetailsViewModel = hiltViewModel()

                    navController.previousBackStackEntry?.savedStateHandle?.get<Movie?>("movie")
                        ?.let { movie ->
                            MovieDetailsScreen(movie = movie, viewModel = viewModel, onBackClick = {
                                navController.popBackStack()
                            })
                        }
                }

                composable(
                    route = "movie_details/{imdbId}",
                    deepLinks = listOf(
                        navDeepLink {
                            uriPattern = "https://movies.devhub.com/movie/{imdbId}"
                        }
                    )
                ) { backStackEntry ->

                    val imdbId = backStackEntry.arguments?.getString("imdbId")!!

                    MovieScreenDL(
                        imdbId = imdbId,
                        viewModel = hiltViewModel(),
                        onBackClick = { navController.popBackStack() }
                    )
                }

                composable(
                    route = Screen.GridScreen.route,
                    arguments = listOf(
                        navArgument("showType") { type = NavType.StringType }
                    )
                ) { backStackEntry ->

                    val showType = backStackEntry.arguments?.getString("showType") ?: ""
                    val homeViewModel: HomeViewModel =
                        hiltViewModel(navController.getBackStackEntry(Screen.HomeScreen.route))

                    val networkViewModel: NetworkViewModel =
                        hiltViewModel(navController.getBackStackEntry(Screen.HomeScreen.route))

                    ShowGridsScreen(
                        title = showType,
                        onBackClick = {
                            navController.popBackStack()
                        },
                        { movie ->
                            navigateToDetails(
                                navController = navController,
                                movie = movie
                            )
                        },
                        homeViewModel,
                        networkViewModel
                    )
                }
            }

        }


    }
}


fun navigateToDetails(navController: NavHostController, movie: Movie) {
    navController.currentBackStackEntry?.savedStateHandle?.set("movie", movie)
    navController.navigate(
        route = Screen.DetailsScreen.route
    )
}


private fun navigateToTab(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { screen_route ->
            popUpTo(screen_route) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}
