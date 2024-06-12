package com.example.rememberme.presentation.people

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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

@Composable
fun PeopleScreenContent(modifier: Modifier = Modifier) {

    val peopleList = remember { FakeDataSource.getPeopleList() }
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        items(count = peopleList.size) { index ->
            PeopleListItem(peopleList[index], {})
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


