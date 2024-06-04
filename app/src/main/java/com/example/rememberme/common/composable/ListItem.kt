package com.example.rememberme.common.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rememberme.R
import com.example.rememberme.ui.theme.RememberMeTheme

@Composable
fun ListItem(name: String, meetPlace: String, modifier: Modifier = Modifier) {
    ListItem(
        leadingContent = {
            Image(
                painter = painterResource(id = R.drawable.ic_f1),
                modifier = Modifier.size(64.dp),
                contentDescription = null
            )
        },
        headlineContent = {
            Text(name)
        },
        supportingContent = {
            Text(meetPlace)
        },
        tonalElevation = 2.dp, // Adjust tonal elevation
        shadowElevation = 4.dp // Adjust shadow elevation
    )
}

@Preview(showBackground = true)
@Composable
fun ListItemPreview() {
    RememberMeTheme {
        ListItem("Omer", "Abu Dhabi")
    }
}