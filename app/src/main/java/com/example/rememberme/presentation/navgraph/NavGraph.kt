package com.example.rememberme.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.rememberme.presentation.onboarding.OnBoardingScreen

@Composable
fun NavGraph(
    startDestination: String
){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
        navigation(
            route = Routes.OnBoardingScreen.route,
            startDestination = Routes.OnBoardingScreen.route,
        ){
            composable(
                route = Routes.OnBoardingScreen.route
            ) {
                OnBoardingScreen(
                )
            }
        }
    }

}