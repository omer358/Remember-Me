@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.omo.rememberme.presentation.addperson

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.view.ContextThemeWrapper
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.omo.rememberme.R
import com.omo.rememberme.presentation.common.composables.CustomButton
import com.omo.rememberme.presentation.common.composables.CustomErrorText
import com.omo.rememberme.presentation.common.composables.CustomOutlinedTextField
import com.omo.rememberme.ui.theme.RememberMeTheme
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

private const val TAG = "AddPersonScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPersonScreen(
    viewModel: AddPersonViewModel = hiltViewModel(),
    popUp: () -> Unit,
    personId: Long?
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isPersonSaved by viewModel.isPersonSaved.collectAsStateWithLifecycle()

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedAvatarResId by remember {
        if (personId == null) {

            mutableIntStateOf(R.drawable.ic_m1)
        } else {

            mutableIntStateOf(uiState.avatar)
        }
    }
    if (isPersonSaved) {
        popUp()
    }

    LaunchedEffect(personId) {
        if (personId != null) {
            viewModel.loadPersonDetails(personId)
        } else {
            viewModel.resetForm()
        }
    }
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

        },
        onAvatarPickerClick = {
            showBottomSheet = true
            scope.launch { sheetState.show() }
        },
        selectedAvatarResId = selectedAvatarResId,
        popUp = popUp,
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

@OptIn(ExperimentalMaterial3Api::class)
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
    popUp: () -> Unit,
    scrollState: ScrollState = rememberScrollState()
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
                .padding(horizontal = 16.dp)
                .padding(paddingValues)
                .verticalScroll(scrollState)
        ) {
            CustomOutlinedTextField(
                value = uiState.firstName,
                onValueChange = onFirstNameChange,
                label = "First Name",
                isError = uiState.firstNameError != null,
                errorText = uiState.firstNameError
            )
            CustomOutlinedTextField(
                value = uiState.secondName,
                onValueChange = onSecondNameChange,
                label = "Second Name",
                isError = uiState.secondNameError != null,
                errorText = uiState.secondNameError
            )
            CustomOutlinedTextField(
                value = uiState.place,
                onValueChange = onPlaceChange,
                label = "Meeting Place",
                isError = uiState.placeError != null,
                errorText = uiState.placeError
            )
            DateTimePicker(
                uiState = uiState,
                onDateTimeChange = onTimeChange,
            )
            GenderRadioButton(
                selectedGender = uiState.gender,
                errorMessage = uiState.genderError,
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
                keyBoardActions = ImeAction.Done,
                isError = false
            )
            Spacer(modifier = Modifier.size(16.dp))
            CustomButton(
                onClick = onSavePerson,
                text = if (uiState.id != null) "Update" else "Save"
            )
        }
    }
}


@Composable
fun DateTimePicker(
    uiState: AddPersonUiState,
    onDateTimeChange: (String) -> Unit,
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val selectedDateTime = remember { mutableStateOf(uiState.time) }

    if (uiState.time.isBlank()) {
        calendar.timeInMillis = System.currentTimeMillis()
    }

    val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault())

    val datePickerDialogContext = ContextThemeWrapper(context, R.style.Theme_RememberMe)
    val timePickerDialogContext = ContextThemeWrapper(context, R.style.Theme_RememberMe)

    val datePickerDialog = DatePickerDialog(
        datePickerDialogContext,
        { _, year: Int, month: Int, dayOfMonth: Int ->
            calendar.set(year, month, dayOfMonth)
            val timePickerDialog = TimePickerDialog(
                timePickerDialogContext,
                { _, hourOfDay: Int, minute: Int ->
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calendar.set(Calendar.MINUTE, minute)
                    selectedDateTime.value = dateFormatter.format(calendar.time)
                    onDateTimeChange(selectedDateTime.value)
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            )
            timePickerDialog.show()
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Column {
        OutlinedButton(onClick = { datePickerDialog.show() }) {
            Text(text = if (selectedDateTime.value.isEmpty()) "Pick Date & Time" else selectedDateTime.value)
        }
        uiState.timeError?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp)
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
    modifier: Modifier = Modifier,
    errorMessage: String?
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
        if (errorMessage != null) {
            CustomErrorText(errorText = errorMessage)
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
            popUp = {},
        )
    }
}
