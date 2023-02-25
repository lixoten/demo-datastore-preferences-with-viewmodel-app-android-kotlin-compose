package com.lixoten.datastore_preferences_viewmodel.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.lixoten.datastore_preferences_viewmodel.data.UserPreferencesKeys.AGE
import com.lixoten.datastore_preferences_viewmodel.data.UserPreferencesKeys.FIRST_NAME
import com.lixoten.datastore_preferences_viewmodel.data.UserPreferencesKeys.LAST_NAME
import com.lixoten.datastore_preferences_viewmodel.data.UserPreferencesKeys.TOGGLE_ICON
import kotlinx.coroutines.flow.*
import java.io.IOException

// Define your data class
data class UserPreferences(
    val firstName: String = "",
    val lastName: String = "",
    val age: Int = 0,
    val toggleIcon: Boolean = false,
)

// Define your preference keys
object UserPreferencesKeys {
    val FIRST_NAME = stringPreferencesKey("first_name")
    val LAST_NAME = stringPreferencesKey("last_name")
    val AGE = intPreferencesKey("age")
    val TOGGLE_ICON = booleanPreferencesKey("toggle_icon")
}

// Define a DataStore class to store and retrieve your data
class UserPreferencesRepository(private val dataStore: DataStore<Preferences>) {

    // Update the user preferences
    suspend fun updateUserPreferences(
        firstName: String,
        lastName: String,
        age: Int,
        //toggleIcon: Boolean,
    ) {
        dataStore.edit { preferences ->
            preferences[FIRST_NAME] = firstName
            preferences[LAST_NAME] = lastName
            preferences[AGE] = age
            //preferences[TOGGLE_ICON] = toggleIcon
        }
    }

    // Update the user preferences
    suspend fun updateUserPreferencesIcon(
        toggleIcon: Boolean,
    ) {
        dataStore.edit { preferences ->
            preferences[TOGGLE_ICON] = toggleIcon
        }
    }

    // Read the user preferences as a Flow
    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                // Handle the exception
            } else {
                throw exception
            }
        }
        .map { preferences ->
            UserPreferences(
                firstName = preferences[FIRST_NAME] ?: "",
                lastName = preferences[LAST_NAME] ?: "",
                age = preferences[AGE] ?: 0,
                toggleIcon = preferences[TOGGLE_ICON] ?: false,
            )
        }
}