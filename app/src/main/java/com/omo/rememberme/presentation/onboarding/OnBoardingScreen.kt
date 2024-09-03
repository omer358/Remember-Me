package com.omo.rememberme.presentation.onboarding

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.omo.rememberme.ui.theme.RememberMeTheme

@Composable
fun OnBoardingScreen(modifier: Modifier = Modifier){
    val viewModel: OnBoardingViewModel = hiltViewModel()
    OnBoardingScreenContent(
        onBoardingEvent = viewModel::onEvent,
        modifier
    )
}

@Composable
fun OnBoardingScreenContent(
    onBoardingEvent: (OnBoardingEvent) -> Unit = {},
    modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "OnBoarding Screen")
        Spacer(Modifier.height(20.dp))
        Button(onClick = {onBoardingEvent(OnBoardingEvent.SaveAppEntry)}) {
            Text(text = "Start")
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun OnBoardingScreenPreview() {
    RememberMeTheme {
        Surface {
            OnBoardingScreenContent()
        }
    }
}