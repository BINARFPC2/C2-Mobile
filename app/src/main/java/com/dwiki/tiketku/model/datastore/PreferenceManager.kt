package com.dwiki.tiketku.model.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferenceManager @Inject constructor(private val dataStore: DataStore<Preferences>) {

    //get login state
    fun getLoginState(): Flow<Boolean> {
        return dataStore.data.map {
            it[LOGIN_STATE] ?: false
        }
    }

    //save login state
    suspend fun saveLoginState(state:Boolean){
        dataStore.edit {
            it[LOGIN_STATE] = state
        }
    }

    //logout
    suspend fun logout(){
        dataStore.edit {
            it.clear()
        }
    }


    companion object{
        private val LOGIN_STATE = booleanPreferencesKey("login_state")
    }
}