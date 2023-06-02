package com.nelc.cakesizer.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Locale

class SettingsStore(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    }

    private val KEY_LANGUAGE = stringPreferencesKey("language")
    private val KEY_QUALITY = intPreferencesKey("quality")

    val languageFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[KEY_LANGUAGE] ?: Locale.getDefault().language
    }

    val qualityFlow: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[KEY_QUALITY] ?: 0
    }

    suspend fun setLanguage(language: String) {
        context.dataStore.edit { settings ->
            settings[KEY_LANGUAGE] = language
        }
    }

    suspend fun setQuality(quality: Int) {
        context.dataStore.edit { settings ->
            settings[KEY_QUALITY] = quality

        }
    }
}