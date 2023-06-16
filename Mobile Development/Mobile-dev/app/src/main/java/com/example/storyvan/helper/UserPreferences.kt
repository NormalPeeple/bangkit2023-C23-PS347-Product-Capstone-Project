package com.example.storyvan.helper

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.storyvan.model.LoginModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences private constructor(private val dataStore: DataStore<Preferences>){

    fun getToken(): Flow<LoginModel> {
        return dataStore.data.map { preferences ->
            LoginModel(
                preferences[key_name] ?: "",
                preferences[key_token] ?: "",
                preferences[key_state] ?: false
            )
        }
    }

    suspend fun saveToken(token: LoginModel) {
        dataStore.edit {preferences ->
            preferences[key_name] = token.name
            preferences[key_token] = token.token
            preferences[key_state] = token.isLogin
        }
    }

    suspend fun login() {
        dataStore.edit { preferences ->
            preferences[key_state] = true
        }
    }

    suspend fun deleteToken() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        @Volatile
        private var instance: UserPreferences? = null
        private val key_name = stringPreferencesKey("name")
        private val key_token = stringPreferencesKey("token")
        private val key_state = booleanPreferencesKey("state")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreferences {
            return instance ?: synchronized(this) {
                val instance = UserPreferences(dataStore)
                this.instance = instance
                instance
            }
        }
    }
}