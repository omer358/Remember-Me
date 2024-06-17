package com.example.rememberme.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.rememberme.R
import com.example.rememberme.domain.model.People
import com.example.rememberme.presentation.details.PersonDetailsScreen
import com.example.rememberme.presentation.details.PersonDetailsViewModel
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
                val viewModel: PersonDetailsViewModel = hiltViewModel()
                PersonDetailsScreen(
                    People(
                        firstName = "William",
                        secondName = "Doe",
                        avatar = R.drawable.ic_m3,
                        time = "10:00 AM",
                        place = "Home",
                        gender = "Male"
                    )
                )
            }
        }
    }

}