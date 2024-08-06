// File path: com/example/rememberme/presentation/settings/SettingsScreen.kt

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
import androidx.compose.material.icons.filled.Notifications
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.rememberme.R
import com.example.rememberme.domain.model.RemindersRepetition
import com.example.rememberme.domain.model.ThemeMode
import com.example.rememberme.ui.theme.RememberMeTheme

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    popUp: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    SettingsScreenContent(
        modifier = modifier,
        onBackClick = popUp,
        state = state,
        onThemeSelected = { themeMode ->
            viewModel.onEvent(SettingsEvent.ChangeTheme(themeMode))
        },
        onRepetitionSelected = { repetition ->
            viewModel.onEvent(SettingsEvent.ChangeRemindersRepetition(repetition))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenContent(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    state: SettingsUiState,
    onThemeSelected: (ThemeMode) -> Unit,
    onRepetitionSelected: (RemindersRepetition) -> Unit
) {
    var showThemeDialog by rememberSaveable { mutableStateOf(false) }
    var showRepetitionDialog by rememberSaveable { mutableStateOf(false) }

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
                    showThemeDialog = true
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
                    Text(text = state.theme.toString())
                },
            )
            ListItem(
                modifier = Modifier.clickable {
                    showRepetitionDialog = true
                },
                headlineContent = {
                    Text(text = "Reminders")
                },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                supportingContent = {
                    Text(text = state.remindersRepetition.toString())
                },
            )
        }
    }
    ThemeSelectionDialog(
        showDialog = showThemeDialog,
        onDismissRequest = { showThemeDialog = false },
        onThemeSelected = { themeMode ->
            onThemeSelected(themeMode)
            showThemeDialog = false
        },
        selectedOption = state.theme
    )
    RemindersRepetitionDialog(
        showDialog = showRepetitionDialog,
        onDismissRequest = { showRepetitionDialog = false },
        onRepetitionSelected = { repetition ->
            onRepetitionSelected(repetition)
            showRepetitionDialog = false
        },
        selectedOption = state.remindersRepetition
    )
}

@Composable
fun RemindersRepetitionDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    onRepetitionSelected: (RemindersRepetition) -> Unit,
    selectedOption: RemindersRepetition
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = {
                Text(text = "Select Reminders Repetition")
            },
            text = {
                Column {
                    ReminderOptionRow(
                        RemindersRepetition.OnceADay,
                        selectedOption,
                        onRepetitionSelected
                    )
                    ReminderOptionRow(
                        RemindersRepetition.ThreeADay,
                        selectedOption,
                        onRepetitionSelected
                    )
                    ReminderOptionRow(
                        RemindersRepetition.FiveADay,
                        selectedOption,
                        onRepetitionSelected
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
                    ThemeOptionRow(
                        ThemeMode.LIGHT,
                        selectedOption,
                        onThemeSelected
                    )
                    ThemeOptionRow(
                        ThemeMode.DARK,
                        selectedOption,
                        onThemeSelected
                    )
                    ThemeOptionRow(
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
        Text(text = themeMode.toString())
    }
}

@Composable
fun ReminderOptionRow(
    repetition: RemindersRepetition,
    selectedOption: RemindersRepetition,
    onOptionSelected: (RemindersRepetition) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onOptionSelected(repetition) }
    ) {
        RadioButton(
            selected = (repetition == selectedOption),
            onClick = { onOptionSelected(repetition) }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = repetition.toString())
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
                state = SettingsUiState(
                    theme = ThemeMode.SYSTEM,
                    remindersRepetition = RemindersRepetition.OnceADay
                ),
                onThemeSelected = {},
                onRepetitionSelected = {}
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
