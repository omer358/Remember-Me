package com.example.rememberme.presentation.common.composables

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rememberme.ui.theme.RememberMeTheme

@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    maxLine: Int = 1,
    keyBoardActions: ImeAction = ImeAction.Next
) {
    val capitalizeFirstLetterTransformation = VisualTransformation { text ->
        val capitalizedText = if (text.text.isNotEmpty()) {
            text.text[0].uppercaseChar() + text.text.substring(1)
        } else {
            text.text
        }
        TransformedText(AnnotatedString(capitalizedText), OffsetMapping.Identity)
    }
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        maxLines = maxLine,
        keyboardOptions = KeyboardOptions(
            imeAction = keyBoardActions,
            keyboardType = KeyboardType.Text
        ),
        visualTransformation = capitalizeFirstLetterTransformation
    )
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun CustomOutlinedTextFieldPreview() {
    RememberMeTheme {
        Surface {
            CustomOutlinedTextField(
                value = "",
                onValueChange = {},
                label = "Label",
            )
        }
    }

}