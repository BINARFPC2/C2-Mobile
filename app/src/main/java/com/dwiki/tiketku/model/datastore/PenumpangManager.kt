package com.dwiki.tiketku.model.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.dwiki.tiketku.model.Penumpang
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PenumpangManager @Inject constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun savePenumpang(penumpang: Penumpang){
        dataStore.edit {
            it[DEWASA] = penumpang.jmlDewasa
            it[ANAK] = penumpang.jmlAnak
            it[BAYI] = penumpang.jmlBayi
        }
    }

    fun getPenumpang(): Flow<Penumpang> {
        return dataStore.data.map {
            Penumpang(
                it[DEWASA] ?: 1,
                it[ANAK] ?: 0,
                it[BAYI] ?: 0
            )
        }
    }


    companion object{
        private val DEWASA = intPreferencesKey("dewasa")
        private val ANAK = intPreferencesKey("anak")
        private val BAYI = intPreferencesKey("bayi")
    }
}