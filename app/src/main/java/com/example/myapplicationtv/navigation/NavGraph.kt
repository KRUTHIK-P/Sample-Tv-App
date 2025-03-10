package com.example.myapplicationtv.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplicationtv.presentation.screen.HomeScreen
import com.example.myapplicationtv.presentation.screen.PlayerScreen
import com.example.myapplicationtv.presentation.viewmodel.VideoViewModel

@Composable
fun NavGraph(navHostController: NavHostController, viewModel: VideoViewModel) {
    NavHost(navController = navHostController, startDestination = Screens.HOME) {
        composable(Screens.HOME) {
            HomeScreen(viewModel) {
                navHostController.navigate(Screens.PLAYER_SCREEN)
            }
        }
        composable(Screens.PLAYER_SCREEN) {
            PlayerScreen(viewModel)
        }
    }
}