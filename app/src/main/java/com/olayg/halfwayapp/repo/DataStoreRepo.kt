package com.olayg.halfwayapp.repo

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.olayg.halfwayapp.model.custom.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class DataStoreRepo(private val dataStore: DataStore<Preferences>) {

    companion object PreferencesKeys {
        val DATA = stringPreferencesKey("data")
    }

    suspend fun saveToDataStore(data: Character) {
        dataStore.edit { preference ->
            preference[DATA] = data.toString()
        }
    }

    val readFromDataStore : Flow<DataPref> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.d("DataStoreRepo: ", exception.message.toString())
                emit(emptyPreferences())
            } else {
                //throw exception
            }
        }
        .map { preference ->
            val data = preference[DATA] ?: Character.empty().toString()
            DataPref(data)
        }

    suspend fun clearDataStore() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    suspend fun removeData() {
        dataStore.edit { preference ->
            preference.remove(DATA)
        }
    }
}

data class DataPref(
    val characterData: String,
)