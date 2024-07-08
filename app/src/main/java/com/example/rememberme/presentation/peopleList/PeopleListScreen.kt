package com.example.rememberme.presentation.peopleList

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.rememberme.R
import com.example.rememberme.presentation.common.composables.LoadingStateScreen
import com.example.rememberme.presentation.common.composables.PeopleListItem
import com.example.rememberme.presentation.peopleList.composable.EmptyStateScreen

private const val TAG = "PeopleListScreen"
@Composable
fun PeopleScreen(
    modifier: Modifier = Modifier,
    viewModel: PeopleViewModel = hiltViewModel(),
    navigateToDetailScreen: (Long) -> Unit,
    navigateToAddNewPersonScreen: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    PeopleScreenContent(state, modifier, navigateToDetailScreen, navigateToAddNewPersonScreen)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeopleScreenContent(
    state: PeopleState,
    modifier: Modifier = Modifier,
    navigateToDetailScreen: (Long) -> Unit,
    navigateToAddNewPersonScreen: () -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(id = R.string.app_name))
                },
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
        if (state.isLoading) {
            Log.d(TAG, "PeopleScreenContent: Loading")
            LoadingStateScreen(modifier = modifier.padding(paddingValues = it))
        } else {
            Log.d(TAG, "PeopleScreenContent: ${state.people}")
            if (state.people.isEmpty()) {
                Log.d(TAG, "PeopleScreenContent: Empty")
                EmptyStateScreen(modifier = modifier.padding(paddingValues = it))
            } else {
                Log.d(TAG, "PeopleScreenContent: ${state.people}")
                LazyColumn(
                    modifier = modifier
                        .padding(it)
                        .fillMaxSize()
                        .testTag("PeopleListScreen")
                ) {
                    items(count = state.people.size) { index ->
                        PeopleListItem(state.people[index], { personId ->
                            navigateToDetailScreen(personId)
                        })
                    }
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Composable
//fun PeopleScreenContentPreview() {
//    RememberMeTheme {
//        PeopleScreenContent(
//            peopleState = remember {
////                mutableStateOf(FakeDataSource.getPeopleList())
//            },
//            navigateToDetailScreen = {},
//            navigateToAddNewPersonScreen = {}
//        )
//    }
//}


