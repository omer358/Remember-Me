package com.omo.rememberme.presentation.details
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.omo.rememberme.R
import com.omo.rememberme.domain.model.People
import com.omo.rememberme.presentation.common.composables.LoadingStateScreen
import com.omo.rememberme.ui.theme.RememberMeTheme
import kotlinx.coroutines.launch

private const val TAG = "PersonDetails"

@Composable
fun PersonDetailsScreen(
    viewModel: PersonDetailsViewModel = hiltViewModel(),
    personId: Long,
    navigateUp: () -> Unit,
    navigateToEditScreen: (Long?) -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()


    LaunchedEffect(personId) {
        coroutineScope.launch {
            viewModel.onEvent(PersonDetailsEvent.LoadPerson(personId))
        }
    }
    when {
        uiState.value.isLoading -> {
            LoadingStateScreen()
        }
        uiState.value.error != null -> {
            ErrorContent(uiState.value.error!!)
        }
        uiState.value.person != null -> {
            PersonDetailsContent(
                uiState.value.person!!,
                navigateUp,
                navigateToEditScreen,
                onDeletePerson = {viewModel.onEvent(PersonDetailsEvent.DeletePerson)},
                onSendNotification = {viewModel.sendNotification()}
            )
        }
        else -> {
            // Optionally, you can add a UI to show "Person not found"
        }
    }
}

@Composable
fun ErrorContent(error: String) {
    // TODO: Implement a custom Error animation in the future
    Column {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = error, style = MaterialTheme.typography.headlineLarge)
    }
}

@Composable
fun PersonDetailsContent(
    person: People,
    navigateUp: () -> Unit,
    navigateToEditScreen: (Long?) -> Unit,
    onDeletePerson: () -> Unit,
    modifier: Modifier = Modifier,
    onSendNotification: () -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {
            Box(
                modifier = Modifier
                    .shadow(elevation = 16.dp)
                    .clip(
                        RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
                    )
                    .background(MaterialTheme.colorScheme.primary)
                    .fillMaxWidth()
                    .height(200.dp)

            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .fillMaxWidth()
                        .padding(top = 26.dp, start = 4.dp),
                ) {
                    IconButton(
                        onClick = {
                            navigateUp()
                        }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    Row {
                        IconButton(
                            onClick = {
                            navigateToEditScreen(person.id)
                        }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                        IconButton(onClick = {
                            onDeletePerson()
                            navigateUp()
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                        IconButton(onClick = {
                            onSendNotification()
                        }){
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }

                }
            }
            Box(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clip(CircleShape)
                    .shadow(elevation = 16.dp)
                    .border(2.dp, MaterialTheme.colorScheme.secondary, CircleShape)
                    .background(MaterialTheme.colorScheme.inversePrimary)
                    .size(120.dp)
                    .align(Alignment.BottomStart)
            ) {
                Image(
                    painter = painterResource(id = person.avatar),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(120.dp)
                )
            }
        }
        Column(
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = person.toString(), style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(text = "Time", style = MaterialTheme.typography.titleLarge)
                    Text(text = person.time, style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Place", style = MaterialTheme.typography.titleLarge)
                    Text(text = person.place, style = MaterialTheme.typography.bodyLarge)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(text = "Gender", style = MaterialTheme.typography.titleLarge)
                    Text(text = person.gender, style = MaterialTheme.typography.bodyLarge)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(text = "Additional Notes:", style = MaterialTheme.typography.titleLarge)
                    person.note?.let { Text(text = it, style = MaterialTheme.typography.bodyLarge) }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PersonDetailsContentPreview() {
    RememberMeTheme {
        PersonDetailsContent(
            person = People(
                firstName = "William",
                secondName = "Doe",
                gender = "Male",
                time = "12:00",
                place = "Home",
                avatar = R.drawable.ic_m4
            ),
            navigateUp = {
            },
            navigateToEditScreen = {},
            onDeletePerson = {},
            onSendNotification = {}
        )
    }
}