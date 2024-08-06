package com.example.rememberme.presentation.peopleList

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.rememberme.R
import com.example.rememberme.presentation.common.composables.LoadingStateScreen
import com.example.rememberme.presentation.common.composables.PeopleListItem
import com.example.rememberme.presentation.peopleList.composable.EmptyStateScreen
import com.example.rememberme.ui.theme.RememberMeTheme

private const val TAG = "PeopleListScreen"

@Composable
fun PeopleScreen(
    modifier: Modifier = Modifier,
    viewModel: PeopleViewModel = hiltViewModel(),
    navigateToDetailScreen: (Long) -> Unit,
    navigateToAddNewPersonScreen: () -> Unit,
    navigateToSettingsScreen: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    PeopleScreenContent(
        state,
        modifier,
        navigateToDetailScreen,
        navigateToAddNewPersonScreen,
        navigateToSettingsScreen
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeopleScreenContent(
    state: PeopleState,
    modifier: Modifier = Modifier,
    navigateToDetailScreen: (Long) -> Unit,
    navigateToAddNewPersonScreen: () -> Unit,
    navigateToSettingsScreen: () -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(id = R.string.app_name))
                },
                actions = {
                    IconButton(
                        onClick = {
                            navigateToSettingsScreen()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Add a new Person"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navigateToAddNewPersonScreen()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add a new Person"
                )
            }
        }
    ) { it ->
        AnimatedContent(
            state,
            transitionSpec = {
                fadeIn(
                    spring(stiffness = Spring.StiffnessMedium)
                ) togetherWith
                        fadeOut(
                            spring(stiffness = Spring.StiffnessMedium)
                        )
            },
            label = "PeopleScreen animatedContent"
        ) { peopleState ->
            if (peopleState.isLoading) {
                LoadingStateScreen(modifier = modifier.padding(paddingValues = it))
            } else {
                if (peopleState.people.isEmpty()) {
                    EmptyStateScreen(modifier = modifier.padding(paddingValues = it))
                } else {
                    LazyColumn(
                        modifier = modifier
                            .padding(it)
                            .fillMaxSize()
                            .testTag("PeopleListScreen")
                    ) {
                        items(count = peopleState.people.size) { index ->
                            PeopleListItem(peopleState.people[index], { personId ->
                                navigateToDetailScreen(personId)
                            })
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PeopleScreenContentPreview() {
    RememberMeTheme {
        PeopleScreenContent(
            state = PeopleState(),
            navigateToDetailScreen = {},
            navigateToAddNewPersonScreen = {},
            navigateToSettingsScreen = {}
        )
    }
}


