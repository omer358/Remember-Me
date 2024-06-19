package com.example.rememberme.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.rememberme.presentation.details.PersonDetailsScreen
import com.example.rememberme.presentation.onboarding.OnBoardingScreen
import com.example.rememberme.presentation.peopleList.PeopleScreen

@Composable
fun NavGraph(
    startDestination: String,
    navController: NavHostController = rememberNavController()
) {

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        navigation(
            route = Routes.AppStartNavigation.route,
            startDestination = Routes.OnBoardingScreen.route,
        ) {
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
        ) {
            composable(
                route = Routes.PeopleListScreen.route
            ) {
                PeopleScreen(navigateToDetailScreen = { personId ->
                    navController.navigate(Routes.PersonDetailsScreen.route + "/$personId")
                })
            }
            composable(
                route = Routes.AddPersonScreen.route,
            ) {
                //TODO add person screen
            }
            composable(
                route = Routes.PersonDetailsScreen.route + "/{personId}",
                arguments = listOf(navArgument("personId") { type = NavType.LongType })
            ) {
                val personId = it.arguments?.getLong("personId")
                if (personId != null) {
                    PersonDetailsScreen(
                        personId = personId,
                        navigateUp = {
                            navController.navigateUp()
                        }
                    )
                }
            }
        }
    }

}