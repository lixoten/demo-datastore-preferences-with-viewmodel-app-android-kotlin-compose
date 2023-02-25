package com.lixoten.datastore_preferences_viewmodel

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lixoten.datastore_preferences_viewmodel.ui.screens.MyViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MyApp(
    viewModel: MyViewModel = viewModel(
        factory = MyViewModel.Factory
    )
) {
    //val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    //val uiState by viewModel.uiState.collectAsState()
    val uiState by viewModel.uiState

    // Just to display current preferences... initial and after save click
    val uiStateCopy by viewModel.uiStateCopy


    Column(
        modifier = Modifier.clickable { keyboardController?.hide() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "DataStore Preferences w/ViewModel Example", fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        Column(horizontalAlignment = Alignment.Start) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Grid or View: ")
                IconButton(
                    onClick = {
                        viewModel.updateToggleIcon(uiState.toggleIcon)
//                        scope.launch {
//                            userPreferencesRepository.updateUserPreferencesIcon(
//                                toggleIcon
//                            )
//                        }
                    }
                ) {
                    Icon(
                        imageVector = if (uiState.toggleIcon) Icons.Default.GridView else Icons.Default.ViewList,
                        contentDescription = null
                    )
                }
            }

            TextField(
                value = uiState.firstName,
                onValueChange = { viewModel.updateFirstName(it) },
                label = { Text(text = "First Name") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = uiState.lastName,
                onValueChange = { viewModel.updateLastName(it) },
                label = { Text(text = "Last Name") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = uiState.age,
                onValueChange = { viewModel.updateAge(it) },
                label = { Text(text = "Age(numeric)") }
            )
            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            ) {
                Text(text = "First: " + uiStateCopy.firstName)
                Text(text = "Last: " + uiStateCopy.lastName)
                Text(text = "Age: " + uiStateCopy.age)
                Text(text = "Toggle: " + uiStateCopy.toggleIcon)

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        viewModel.updatePreferences(uiState)
                        viewModel.updatePreferenceToggle(uiState.toggleIcon)
                    }
                ) {
                    Text(text = "Save Settings")
                }
            }
        }
    }
}