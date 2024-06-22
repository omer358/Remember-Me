package com.example.rememberme.presentation.onboarding

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.rememberme.ui.theme.RememberMeTheme

@Composable
fun OnBoardingScreen(modifier: Modifier = Modifier){
    OnBoardingScreenContent(modifier)
}

@Composable
fun OnBoardingScreenContent(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "OnBoarding Screen")
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun OnBoardingScreenPreview() {
    RememberMeTheme {
        OnBoardingScreenContent()
    }
}