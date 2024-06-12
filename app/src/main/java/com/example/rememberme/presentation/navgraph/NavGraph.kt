package com.example.rememberme.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.rememberme.presentation.onboarding.OnBoardingScreen
import com.example.rememberme.presentation.people.PeopleScreen

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
            route = Routes.AppStartNavigation.route,
            startDestination = Routes.OnBoardingScreen.route,
        ){
            composable(
                route = Routes.OnBoardingScreen.route
            ) {
                OnBoardingScreen(
                )
            }
        }
        navigation(
            route = Routes.PeopleNavigation.route,
            startDestination = Routes.PeopleListScreen.route,
        ){
            composable(
                route = Routes.PeopleListScreen.route
            ){
                PeopleScreen()
            }
            composable(
                route = Routes.AddPersonScreen.route,
            ){
                //TODO add person screen
            }
            composable(
                route = Routes.PersonDetailsScreen.route
            ){
                //TODO person details screen
            }
        }
    }

}