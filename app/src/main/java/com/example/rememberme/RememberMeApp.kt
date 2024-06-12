package com.example.rememberme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.rememberme.presentation.people.PeopleScreen
import com.example.rememberme.ui.theme.RememberMeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RememberMeApp() {
    RememberMeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(stringResource(id = R.string.app_name))
                        }
                    )
                }
            ) {
                PeopleScreen(modifier = Modifier.padding(it))
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RememberMeAppPreview() {
    RememberMeApp()
}
