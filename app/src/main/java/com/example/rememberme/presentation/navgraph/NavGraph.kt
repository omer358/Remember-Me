package com.example.rememberme.presentation.navgraph

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.example.rememberme.presentation.addperson.AddPersonScreen
import com.example.rememberme.presentation.details.PersonDetailsScreen
import com.example.rememberme.presentation.onboarding.OnBoardingScreen
import com.example.rememberme.presentation.peopleList.PeopleScreen
import com.example.rememberme.presentation.settings.SettingsScreen

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
                AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(700, easing = FastOutSlowInEasing)

            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(700)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                animationSpec = tween(700)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                animationSpec = tween(700)
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
                    },
                    navigateToSettingsScreen = {
                        navController.navigate(Routes.SettingsScreen.route)
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
                arguments = listOf(navArgument("personId") {
                    type = NavType.StringType; nullable = true
                })
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
                arguments = listOf(navArgument("personId") { type = NavType.StringType }),
                deepLinks = listOf(navDeepLink { uriPattern = "app://people/{personId}" })
            ) { it ->
                val personId = it.arguments?.getString("personId")?.toLong()
                if (personId != null) {
                    PersonDetailsScreen(
                        personId = personId,
                        navigateUp = {
                            navController.navigateUp()
                        },
                        navigateToEditScreen = { id ->
                            navController.navigate("${Routes.AddPersonScreen.route}/$id")
                        }
                    )
                }
            }
            composable(route = Routes.SettingsScreen.route) {
                SettingsScreen(
                    popUp = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}