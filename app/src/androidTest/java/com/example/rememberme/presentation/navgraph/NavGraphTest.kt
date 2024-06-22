package com.example.rememberme

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.rememberme.presentation.navgraph.NavGraph
import com.example.rememberme.presentation.navgraph.Routes
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavGraphTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testNavigationToOnBoardingScreen() {
        composeTestRule.setContent {
            NavGraph(startDestination = Routes.AppStartNavigation.route)
        }

        composeTestRule.onNodeWithText("OnBoarding Screen").assertIsDisplayed()
    }

    @Test
    fun testNavigationToPeopleScreen() {
        composeTestRule.setContent {
            NavGraph(startDestination = Routes.PeopleNavigation.route)
        }

        composeTestRule.onNodeWithTag("PeopleListScreen").assertIsDisplayed()
    }
}
