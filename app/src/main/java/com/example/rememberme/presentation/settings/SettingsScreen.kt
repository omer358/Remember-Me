package com.example.rememberme.presentation.settings

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.rememberme.R
import com.example.rememberme.domain.model.ThemeMode
import com.example.rememberme.ui.theme.RememberMeTheme

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    popUp: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state by viewModel.themeMode.collectAsStateWithLifecycle()
    viewModel.onEvent(SettingsEvent.GetTheme)
    SettingsScreenContent(
        modifier = modifier,
        onBackClick = popUp,
        state = state,
        onThemeSelected = { themeMode ->
            viewModel.onEvent(SettingsEvent.ChangeTheme(themeMode))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenContent(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    state: ThemeMode,
    onThemeSelected: (ThemeMode) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Settings") },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
            )
        }
    ) {
        Column(
            modifier
                .fillMaxSize()
                .padding(it),

            ) {
            ListItem(
                modifier = Modifier.clickable {
                    showDialog = true
                },
                headlineContent = {
                    Text(text = "Theme")
                },
                leadingContent = {
                    Icon(
                        painter = painterResource(id = R.drawable.light_mode),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                supportingContent = {
                    Text(text = state.toString())
                },
            )
        }
    }
    ThemeSelectionDialog(
        showDialog = showDialog,
        onDismissRequest = { showDialog = false },
        onThemeSelected = { themeMode ->
            onThemeSelected(themeMode)
        },
        selectedOption = state
    )
}

@Composable
fun ThemeSelectionDialog(
    showDialog: Boolean,
    selectedOption: ThemeMode,
    onDismissRequest: () -> Unit,
    onThemeSelected: (ThemeMode) -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = {
                Text(text = "Select Theme")
            },
            text = {
                Column {
                    ThemeOptionRow("Light", ThemeMode.LIGHT, selectedOption, onThemeSelected)
                    ThemeOptionRow("Dark", ThemeMode.DARK, selectedOption, onThemeSelected)
                    ThemeOptionRow(
                        "System Default",
                        ThemeMode.SYSTEM,
                        selectedOption,
                        onThemeSelected
                    )
                }
            },
            confirmButton = {
                Button(onClick = onDismissRequest) {
                    Text("OK")
                }
            }
        )
    }
}

@Composable
fun ThemeOptionRow(
    label: String,
    themeMode: ThemeMode,
    selectedOption: ThemeMode,
    onOptionSelected: (ThemeMode) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onOptionSelected(themeMode) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = (themeMode == selectedOption),
            onClick = { onOptionSelected(themeMode) }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label)
    }
}


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SettingsScreenPreview() {
    RememberMeTheme {
        Surface {
            SettingsScreenContent(
                onBackClick = {},
                state = ThemeMode.SYSTEM,
                onThemeSelected = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AlertDialogExamplePreview() {
    RememberMeTheme {
        Surface {
            ThemeSelectionDialog(
                showDialog = true,
                onDismissRequest = {},
                onThemeSelected = {},
                selectedOption = ThemeMode.SYSTEM
            )
        }
    }
}