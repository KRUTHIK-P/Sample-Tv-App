package com.example.myapplicationtv.data.preferences

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferenceManager @Inject constructor(
    @ApplicationContext val context: Context
) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

    companion object {
        val DARK_THEME_KEY = booleanPreferencesKey("dark_mode")
    }

    val darkTheme = context.dataStore.data
        .map { preferences ->
            preferences[DARK_THEME_KEY] ?: false // Default to 0 if not found
        }

    private suspend fun saveTheme(isDarkMode: Boolean) {
        context.dataStore.edit { preferences ->
            Log.d("theme2", "$isDarkMode")
            preferences[DARK_THEME_KEY] = isDarkMode
        }
    }

    suspend fun updateTheme() {
        val isDarkMode = darkTheme.first() // Get current theme (blocking for simplicity)
        Log.d("theme1", "$isDarkMode")
        saveTheme(!isDarkMode)
    }
}