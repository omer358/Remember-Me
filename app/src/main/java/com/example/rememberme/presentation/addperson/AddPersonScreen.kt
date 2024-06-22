@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.rememberme.presentation.addperson

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rememberme.presentation.common.composables.CustomButton
import com.example.rememberme.presentation.common.composables.CustomOutlinedTextField
import com.example.rememberme.ui.theme.RememberMeTheme

@Composable
fun AddPersonScreen(
    viewModel: AddPersonViewModel = hiltViewModel(),
    popUp: () -> Unit,
) {
    val uiState = viewModel.uiState.collectAsState().value

    AddPersonContent(
        uiState,
        onFirstNameChange = { viewModel.onEvent(AddPersonEvents.OnFirstNameChange(it)) },
        onSecondNameChange = { viewModel.onEvent(AddPersonEvents.OnSecondNameChange(it)) },
        onPlaceChange = { viewModel.onEvent(AddPersonEvents.OnPlaceChange(it)) },
        onTimeChange = { viewModel.onEvent(AddPersonEvents.OnTimeChange(it)) },
        onNoteChange = { viewModel.onEvent(AddPersonEvents.OnNoteChange(it)) },
        onGenderChange = { viewModel.onEvent(AddPersonEvents.OnGenderChange(it)) },
        onAvatarChange = { viewModel.onEvent(AddPersonEvents.OnAvatarChange(it)) },
        onSavePerson = { viewModel.onEvent(AddPersonEvents.OnSavePerson) },
        popUp = popUp
        )
}

@Composable
fun AddPersonContent(
    uiState: AddPersonUiState,
    onFirstNameChange: (String) -> Unit,
    onSecondNameChange: (String) -> Unit,
    onPlaceChange: (String) -> Unit,
    onTimeChange: (String) -> Unit,
    onNoteChange: (String) -> Unit,
    onGenderChange: (String) -> Unit,
    onAvatarChange: (String) -> Unit,
    onSavePerson: () -> Unit,
    popUp: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Add Person")
                },
                navigationIcon = {
                    IconButton(onClick = { popUp() }) {
                        Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
                .padding(paddingValues = it)
        ) {
            CustomOutlinedTextField(
                value = uiState.firstName,
                onValueChange = onFirstNameChange,
                label = "First Name"
            )
            CustomOutlinedTextField(
                value = uiState.secondName,
                onValueChange = onSecondNameChange,
                label = "Second Name"
            )
            CustomOutlinedTextField(
                value = uiState.place,
                onValueChange = onPlaceChange,
                label = "Meeting Place"
            )
            CustomOutlinedTextField(
                value = uiState.time,
                onValueChange = onTimeChange,
                label = "Meeting Time"
            )
            GenderRadioButton(
                selectedGender = uiState.gender,
                onGenderSelected = onGenderChange
            )
            CustomOutlinedTextField(
                value = uiState.avatar.toString(),
                onValueChange = onAvatarChange,
                label = "Avatar"
            )
            CustomOutlinedTextField(
                value = uiState.note ?: "",
                onValueChange = onNoteChange,
                label = "Note"
            )
            CustomButton(
                onClick = onSavePerson,
                text = "Save"
            )
        }
    }
}


@Composable
fun GenderRadioButton(
    selectedGender: String,
    onGenderSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val genderOptions = listOf("Male", "Female")
    Column(modifier = modifier) {
        Text("Gender", modifier = Modifier.padding(bottom = 4.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ){
        genderOptions.forEach { gender ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = (gender == selectedGender),
                    onClick = { onGenderSelected(gender) }
                )
                Text(text = gender, modifier = Modifier.padding(start = 4.dp))
            }
        }
    }
        }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun AddPersonContentPreview() {
    RememberMeTheme {
        AddPersonContent(
            uiState = AddPersonUiState(),
            onFirstNameChange = {},
            onSecondNameChange = {},
            onPlaceChange = {},
            onTimeChange = {},
            onNoteChange = {},
            onGenderChange = {},
            onAvatarChange = {},
            onSavePerson = {},
            popUp = {}
        )
    }
}
