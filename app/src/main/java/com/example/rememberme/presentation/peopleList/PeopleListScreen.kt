package com.example.rememberme.presentation.peopleList

import android.content.res.Configuration
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
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rememberme.R
import com.example.rememberme.data.FakeDataSource
import com.example.rememberme.domain.model.People
import com.example.rememberme.presentation.common.composables.PeopleListItem
import com.example.rememberme.ui.theme.RememberMeTheme

@Composable
fun PeopleScreen(
    modifier: Modifier = Modifier,
    viewModel: PeopleViewModel = hiltViewModel(),
    navigateToDetailScreen: (Long) -> Unit,
    navigateToAddNewPersonScreen:() -> Unit
) {
    val people = viewModel.people.collectAsState(initial = emptyList())
    PeopleScreenContent(people, modifier, navigateToDetailScreen, navigateToAddNewPersonScreen)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeopleScreenContent(
    peopleState: State<List<People>>,
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
        LazyColumn(
            modifier = modifier
                .padding(it)
                .fillMaxSize()
                .testTag("PeopleListScreen")
        ) {
            items(count = peopleState.value.size) { index ->
                PeopleListItem(peopleState.value[index], { personId ->
                    navigateToDetailScreen(personId)
                })
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
            peopleState = remember {
                mutableStateOf(FakeDataSource.getPeopleList())
            },
            navigateToDetailScreen = {},
            navigateToAddNewPersonScreen = {}
        )
    }
}


