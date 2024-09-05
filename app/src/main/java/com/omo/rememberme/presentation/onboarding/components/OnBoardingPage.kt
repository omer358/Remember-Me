package com.omo.rememberme.presentation.onboarding.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.omo.rememberme.presentation.onboarding.Page
import com.omo.rememberme.presentation.onboarding.pages

@Composable
fun OnBoardingPage(
    page: Page,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Spacer(Modifier.height(80.dp))
        Image(
            painter = painterResource(id = page.image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .fillMaxHeight(0.5f),
            contentScale = ContentScale.FillWidth
        )
        Text(
            text = page.title,
            style = MaterialTheme
                .typography
                .displaySmall.copy(
                    fontWeight = FontWeight.Bold
                ),
//            color = colorResource(id = R.color.display_small),
            modifier = Modifier
                .padding(horizontal = 24.dp)
        )
        Text(
            text = page.description,
            style = MaterialTheme
                .typography
                .bodyMedium,
//            color = colorResource(id = R.color.text_medium),
            modifier = Modifier
                .padding(horizontal = 24.dp)
        )
    }
}

@Preview(
    device = "id:pixel_8_pro", showSystemUi = true, showBackground = true,
)
@Preview(
    device = "id:pixel_8_pro", showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun OnBoardingPagePreview() {
    OnBoardingPage(page = pages.last())
}