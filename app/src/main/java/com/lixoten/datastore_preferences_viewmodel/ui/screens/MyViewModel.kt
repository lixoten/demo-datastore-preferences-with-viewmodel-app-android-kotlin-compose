package com.lixoten.datastore_preferences_viewmodel.ui.screens

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.lixoten.demomvvm.MyAppApplication
import com.lixoten.datastore_preferences_viewmodel.data.UserPreferencesRepository
import kotlinx.coroutines.launch

data class MyUiState(
    val firstName: String = "",
    val lastName: String = "",
    val age: String = "",
    val toggleIcon: Boolean = false,
)

class MyViewModel(private val userPreferencesRepository: UserPreferencesRepository): ViewModel() {

    private val _uiState = mutableStateOf(MyUiState())
    val uiState: State<MyUiState> = _uiState
    //private val _uiState = MutableStateFlow(MyUiState())
    //val uiState: StateFlow<MyUiState> = _uiState.asStateFlow()

    private val _uiStateCopy = mutableStateOf(MyUiState())
    val uiStateCopy: State<MyUiState> = _uiStateCopy

    init {
        viewModelScope.launch {
            userPreferencesRepository.userPreferencesFlow.collect {
                _uiState.value = uiState.value.copy(
                    firstName = it.firstName,
                    lastName = it.lastName,
                    age = it.age.toString(),
                    toggleIcon = it.toggleIcon,
                )
                _uiStateCopy.value = uiState.value
            }
        }
    }

    /*
    * save the selection in DataStore through [userPreferencesRepository]
    */
    fun updatePreferences(uiState: MyUiState) {
        viewModelScope.launch {
            userPreferencesRepository.updateUserPreferences(uiState.firstName, uiState.lastName, uiState.age.toInt())
        }

    }
    fun updatePreferenceToggle(tog: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.updateUserPreferencesIcon(tog)
        }

    }

    fun updateFirstName(newValue: String){
        _uiState.value = uiState.value.copy(
            firstName = newValue
        )
    }
    fun updateLastName(newValue: String){
        _uiState.value = uiState.value.copy(
            lastName = newValue
        )
    }
    fun updateAge(newValue: String){
        _uiState.value = uiState.value.copy(
            age = newValue
        )
    }

    fun updateToggleIcon(tog: Boolean){
        _uiState.value = uiState.value.copy(
            toggleIcon = !tog
        )
        //updatePreferenceToggle(tog)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyAppApplication)

                // MyViewModel()
                MyViewModel(application.userPreferencesRepository)
            }
        }
    }
}