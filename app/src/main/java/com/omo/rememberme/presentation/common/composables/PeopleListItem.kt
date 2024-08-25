package com.omo.rememberme.presentation.common.composables

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.omo.rememberme.R
import com.omo.rememberme.domain.model.People
import com.omo.rememberme.ui.theme.RememberMeTheme
import com.omo.rememberme.utils.formatElapsedTime

@Composable
fun PeopleListItem(
    people: People,
    onClickListener: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    ListItem(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .clickable {
                onClickListener(people.id)
            },
        leadingContent = {
            Image(
                painter = painterResource(people.avatar),
                modifier = Modifier.size(64.dp),
                contentDescription = null
            )
        },
        headlineContent = {
            Text(people.firstName +" "+ people.secondName)
        },
        supportingContent = {
            Text(people.place)
        },
        trailingContent = {
            Text(formatElapsedTime(people.time))
        },
        tonalElevation = 2.dp, // Adjust tonal elevation
        shadowElevation = 4.dp // Adjust shadow elevation,
    )
}

@Preview()
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ListItemPreview() {
    RememberMeTheme {
        PeopleListItem(
            people = People(
                firstName = "Sara",
                secondName = "Mustafa",
                place = "London",
                id = 1L,
                avatar = R.drawable.ic_f4,
                gender = "Female",
                time = "10:10 AM"
            )
            , {})
    }
}