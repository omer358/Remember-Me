package com.example.rememberme.presentation.navgraph

import android.util.Log
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.rememberme.presentation.addperson.AddPersonScreen
import com.example.rememberme.presentation.details.PersonDetailsScreen
import com.example.rememberme.presentation.onboarding.OnBoardingScreen
import com.example.rememberme.presentation.peopleList.PeopleScreen

private const val TAG = "NavGraph"
@Composable
fun NavGraph(
    startDestination: String,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = {
           slideIntoContainer(
                animationSpec = tween(300, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Start
            )
        },
        exitTransition = {
            slideOutOfContainer(
                animationSpec = tween(300, easing = EaseOut),
                towards = AnimatedContentTransitionScope.SlideDirection.End
            )
        }
    ) {
        navigation(
            route = Routes.AppStartNavigation.route,
            startDestination = Routes.OnBoardingScreen.route,
        ) {
            composable(
                route = Routes.OnBoardingScreen.route
            ) {
                OnBoardingScreen()
            }
        }
        navigation(
            route = Routes.PeopleNavigation.route,
            startDestination = Routes.PeopleListScreen.route,
        ) {
            composable(
                route = Routes.PeopleListScreen.route
            ) {
                PeopleScreen(
                    navigateToDetailScreen = { personId ->
                        navController.navigate("${Routes.PersonDetailsScreen.route}/$personId")
                    },
                    navigateToAddNewPersonScreen = {
                        navController.navigate(Routes.AddPersonScreen.route)
                    }
                )
            }
            composable(route = Routes.AddPersonScreen.route) {
                AddPersonScreen(
                    popUp = { navController.navigateUp() },
                    personId = null // Explicitly pass null
                )
            }
            composable(
                route = "${Routes.AddPersonScreen.route}/{personId}",
                arguments = listOf(navArgument("personId") { type = NavType.StringType; nullable = true })
            ) {
                val personId = it.arguments?.getString("personId")?.toLongOrNull()

                AddPersonScreen(
                    popUp = {
                        navController.navigateUp()
                    },
                    personId = personId
                )
            }
            composable(
                route = "${Routes.PersonDetailsScreen.route}/{personId}",
                arguments = listOf(navArgument("personId") { type = NavType.StringType })
            ) { it ->
                val personId = it.arguments?.getString("personId")?.toLong()
                if (personId != null) {
                    PersonDetailsScreen(
                        personId = personId,
                        navigateUp = {
                            navController.navigateUp()
                        },
                        navigateToEditScreen = {id ->
                            Log.i(TAG, "NavGraph: NavigateToEditScreen triggered")
                            navController.navigate("${Routes.AddPersonScreen.route}/$id")
                        }
                    )
                }
            }
        }
    }
}
