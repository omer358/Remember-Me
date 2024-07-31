package com.example.rememberme.presentation.navgraph

sealed class Routes(
    val route: String
) {

    data object OnBoardingScreen : Routes("onboardingScreen")
    data object PeopleListScreen : Routes("peopleListScreen")
    data object PersonDetailsScreen : Routes("personDetailScreen")
    data object AddPersonScreen : Routes("addPersonScreen")
    data object AppStartNavigation : Routes("appStartNavigation")
    data object PeopleNavigation : Routes("peopleNavigation")
    data object SettingsScreen : Routes("settingScreen")
}