package com.example.rememberme.presentation.navgraph

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class NavGraphTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

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
