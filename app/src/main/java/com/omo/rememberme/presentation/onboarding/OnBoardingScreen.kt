@file:OptIn(ExperimentalFoundationApi::class)

package com.omo.rememberme.presentation.onboarding

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsapp.presentation.onboarding.components.PageIndicator
import com.omo.rememberme.presentation.onboarding.components.OnBoardingPage
import com.omo.rememberme.ui.theme.RememberMeTheme
import kotlinx.coroutines.launch

@Composable
fun OnBoardingScreen(modifier: Modifier = Modifier) {
    val viewModel: OnBoardingViewModel = hiltViewModel()
    OnBoardingScreenContent(
        modifier,
        onBoardingEvent = viewModel::onEvent
    )
}

@Composable
fun OnBoardingScreenContent(
    modifier: Modifier = Modifier,
    onBoardingEvent: (OnBoardingEvent) -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxSize().navigationBarsPadding().systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val pagerState = rememberPagerState(initialPage = 0) {
            pages.size
        }
        val buttonState = remember {
            derivedStateOf {
                when (pagerState.currentPage) {
                    0 -> listOf("", "Next")
                    1 -> listOf("Back", "Next")
                    2 -> listOf("Back", "Get Started")
                    else -> listOf("", "")
                }
            }
        }
        TextButton(
            onClick = { onBoardingEvent(OnBoardingEvent.SaveAppEntry) },
            modifier = Modifier.align(Alignment.End)) {
            Text(
                text = "Skip",
                style = TextStyle(
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                )
            )

        }
        HorizontalPager(state = pagerState) { index ->
            OnBoardingPage(page = pages[index])

        }
        Spacer(modifier = Modifier.weight(1f))
        PageIndicator(
            pageSize = pages.size,
            selectedPage = pagerState.currentPage,
            modifier = Modifier.width(52.dp)

        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .navigationBarsPadding(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val scope = rememberCoroutineScope()
            if (buttonState.value[0].isNotEmpty()) {
                Button(
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }
                ) {
                    Text(buttonState.value[0])
                }
            }
            Button(
                onClick = {
                    scope.launch {
                        if (pagerState.currentPage == 2) {
                            onBoardingEvent(OnBoardingEvent.SaveAppEntry)
                        } else
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
            ) {
                Text(buttonState.value[1])
            }
        }
        Spacer(modifier = Modifier.weight(0.5f))
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