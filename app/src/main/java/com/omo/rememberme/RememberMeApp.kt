package com.omo.rememberme

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.omo.rememberme.domain.model.ThemeMode
import com.omo.rememberme.presentation.navgraph.NavGraph
import com.omo.rememberme.presentation.settings.SettingsViewModel
import com.omo.rememberme.ui.theme.RememberMeTheme


private const val TAG = "RememberMeApp"

@Composable
fun RememberMeApp(startDestination: String) {
    val viewModel: SettingsViewModel = hiltViewModel()


    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val themeMode = uiState.theme
    val isDarkTheme = when (themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }
    RememberMeTheme(
        darkTheme = isDarkTheme
    ){
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            NavGraph(
                startDestination = startDestination
            )
        }
    }
}


@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun RememberMeAppPreview() {
    RememberMeApp(startDestination = "onBoardingScreen")
}
