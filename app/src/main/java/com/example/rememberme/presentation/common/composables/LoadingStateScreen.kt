package com.example.rememberme.presentation.common.composables

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.rememberme.ui.theme.RememberMeTheme

private const val TAG = "LoadingStateScreen"

@Composable
fun LoadingStateScreen(modifier: Modifier = Modifier) {
    //TODO: FIX THE LOTTIE ANIMATION.
    Log.i(TAG, "LoadingStateScreen")
//    val composition = rememberLottieComposition(
//        spec = LottieCompositionSpec.RawRes(R.raw.loading)
//    )
//    Log.i(TAG, "composition: ${composition.value}")

//    val isPlaying by remember {
//        mutableStateOf(true)
//    }
//    val progress by animateLottieCompositionAsState(
//        composition = composition.value,
//        iterations = LottieConstants.IterateForever,
//        isPlaying = isPlaying
//    )
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        LottieAnimation(
//            composition = composition.value
//        )
        CircularProgressIndicator()
        Text(text = "Loading..",
            style = MaterialTheme.typography.bodyLarge
        )

    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun LoadingStateScreenPreview() {
    RememberMeTheme {
        Surface {
            LoadingStateScreen()
        }
    }
}