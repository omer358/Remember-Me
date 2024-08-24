package com.omo.rememberme.presentation.peopleList.composable

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.omo.rememberme.R
import com.omo.rememberme.ui.theme.RememberMeTheme

@Composable
fun EmptyStateScreen(
    modifier: Modifier = Modifier
){
    Column (
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id = R.drawable.ic_empty_state_vector),
            contentDescription = null,
            modifier = Modifier.size(300.dp)
        )
        Text(
            text = "Go out and Be social!",
            style = MaterialTheme.typography.titleLarge
        )
    }

}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun EmptyStateScreenPreview(){
    RememberMeTheme {
        Surface {
            EmptyStateScreen()
        }
    }
}