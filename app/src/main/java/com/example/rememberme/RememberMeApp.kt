package com.example.rememberme

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.rememberme.presentation.navgraph.NavGraph
import com.example.rememberme.presentation.navgraph.Routes
import com.example.rememberme.ui.theme.RememberMeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RememberMeApp() {
    RememberMeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
                NavGraph(startDestination = Routes.PeopleNavigation.route)
        }
    }
}


@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun RememberMeAppPreview() {
    RememberMeApp()
}
