@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.rememberme.presentation.addperson

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rememberme.R
import com.example.rememberme.presentation.common.composables.CustomButton
import com.example.rememberme.presentation.common.composables.CustomOutlinedTextField
import com.example.rememberme.ui.theme.RememberMeTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPersonScreen(
    viewModel: AddPersonViewModel = hiltViewModel(),
    popUp: () -> Unit,
) {
    val uiState = viewModel.uiState.collectAsState().value
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedAvatarResId by remember { mutableIntStateOf(R.drawable.ic_m1) }

    AddPersonContent(
        uiState = uiState,
        onFirstNameChange = { viewModel.onEvent(AddPersonEvents.OnFirstNameChange(it)) },
        onSecondNameChange = { viewModel.onEvent(AddPersonEvents.OnSecondNameChange(it)) },
        onPlaceChange = { viewModel.onEvent(AddPersonEvents.OnPlaceChange(it)) },
        onTimeChange = { viewModel.onEvent(AddPersonEvents.OnTimeChange(it)) },
        onNoteChange = { viewModel.onEvent(AddPersonEvents.OnNoteChange(it)) },
        onGenderChange = { viewModel.onEvent(AddPersonEvents.OnGenderChange(it)) },
        onSavePerson = {
            viewModel.onEvent(AddPersonEvents.OnSavePerson)
            popUp()
        },
        onAvatarPickerClick = {
            showBottomSheet = true
            scope.launch { sheetState.show() }
        },
        selectedAvatarResId = selectedAvatarResId,
        popUp = popUp
    )

    if (showBottomSheet) {
        AvatarPicker(
            onAvatarSelected = {
                selectedAvatarResId = it
                viewModel.onEvent(AddPersonEvents.OnAvatarChange(it))
                scope.launch { sheetState.hide() }
                showBottomSheet = false
            },
            onDismissRequest = {
                scope.launch { sheetState.hide() }
                showBottomSheet = false
            }
        )
    }
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
    onSavePerson: () -> Unit,
    onAvatarPickerClick: () -> Unit,
    selectedAvatarResId: Int,
    popUp: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Add Person") },
                navigationIcon = {
                    IconButton(onClick = { popUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
                .padding(paddingValues)
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = selectedAvatarResId),
                    contentDescription = null,
                    modifier = Modifier.size(70.dp)
                )
                OutlinedButton(onClick = onAvatarPickerClick) {
                    Text(text = "Pick an avatar")
                }
            }
            CustomOutlinedTextField(
                value = uiState.note ?: "",
                onValueChange = onNoteChange,
                label = "Note",
                keyBoardActions = ImeAction.Done
            )
            Spacer(modifier = Modifier.size(16.dp))
            CustomButton(
                onClick = onSavePerson,
                text = "Save"
            )
        }
    }
}

@Composable
fun AvatarPicker(
    onAvatarSelected: (Int) -> Unit,
    onDismissRequest: () -> Unit
) {
    ModalBottomSheet(onDismissRequest = onDismissRequest) {
        val listOfMaleAvatars = remember {
            listOf(
                R.drawable.ic_m1,
                R.drawable.ic_m2,
                R.drawable.ic_m3,
                R.drawable.ic_m4,
                R.drawable.ic_m5
            )
        }
        val listOfFemaleAvatars = remember {
            listOf(
                R.drawable.ic_f1,
                R.drawable.ic_f2,
                R.drawable.ic_f3,
                R.drawable.ic_f4,
                R.drawable.ic_f5
            )
        }
        val allAvatars = remember {
            listOfMaleAvatars + listOfFemaleAvatars
        }
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                "Pick an Avatar",
                style = MaterialTheme.typography.displaySmall
            )
            Spacer(modifier = Modifier.size(16.dp))
            LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 90.dp)) {
                items(allAvatars.size) { index ->
                    Image(
                        painter = painterResource(id = allAvatars[index]),
                        contentDescription = null,
                        modifier = Modifier
                            .size(70.dp)
                            .padding(vertical = 4.dp)
                            .clickable { onAvatarSelected(allAvatars[index]) }
                    )
                }
            }
            Spacer(modifier = Modifier.size(16.dp))
            Button(
                onClick = { /* Save button action if needed */ },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Save")
            }
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
        ) {
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
            onSavePerson = {},
            onAvatarPickerClick = {},
            selectedAvatarResId = R.drawable.ic_m1,
            popUp = {}
        )
    }
}
