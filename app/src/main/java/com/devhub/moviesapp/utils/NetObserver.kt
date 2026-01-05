package com.devhub.moviesapp.utils

import NoInternetDialog
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Observer
import com.devhub.moviesapp.presentation.home.HomeViewModel

@Composable
fun NetworkObserver(
    networkViewModel: NetworkViewModel,
    homeViewModel: HomeViewModel,
    content: @Composable () -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    var isConnected by remember { mutableStateOf(true) }
    var userDismissed by remember { mutableStateOf(false) }
    var isCached by remember { mutableStateOf(true) }

    val showDialog = !isConnected && !userDismissed

    DisposableEffect(lifecycleOwner) {
        val observer = Observer<Boolean> { connected ->
            isConnected = connected

            if (connected) {
                userDismissed = false
            }
        }

        networkViewModel.isConnected.observe(lifecycleOwner, observer)

        onDispose {
            networkViewModel.isConnected.removeObserver(observer)
        }
    }




    Box {
        content()

        if (showDialog) {
            NoInternetDialog(
                onDismiss = {
                    userDismissed = true
                },
                onRetry = {
                    userDismissed = false
                    if (isConnected) {
                        homeViewModel.onInternetAvailable()
                    }
                }
            )
        } else {
            if (isConnected) {
                homeViewModel.onInternetAvailable()
            }
        }
    }
}



