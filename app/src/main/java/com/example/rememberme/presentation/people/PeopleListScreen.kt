package com.example.rememberme.presentation.people

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.rememberme.R
import com.example.rememberme.data.FakeDataSource
import com.example.rememberme.presentation.composable.PeopleListItem
import com.example.rememberme.ui.theme.RememberMeTheme

@Composable
fun PeopleScreen(
    modifier: Modifier = Modifier,
    viewModel: PeopleViewModel = PeopleViewModel()
) {
    PeopleScreenContent(modifier)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeopleScreenContent(modifier: Modifier = Modifier) {

    val peopleList = remember { FakeDataSource.getPeopleList() }
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

            }
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add a new Person"
            )
        }
    }
    ){
        LazyColumn(
            modifier = modifier.padding(it)
                .fillMaxSize()
        ) {
            items(count = peopleList.size) { index ->
                PeopleListItem(peopleList[index], {})
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PeopleScreenContentPreview() {
    RememberMeTheme {
        PeopleScreenContent()
    }
}


