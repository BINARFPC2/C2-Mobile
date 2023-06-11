package com.dwiki.tiketku.model.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.dwiki.tiketku.model.KelasPenumpang
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class KelasManager @Inject constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun saveKelas(kelas:KelasPenumpang){
        dataStore.edit {
            it[NAME] = kelas.namaKelas
            it[PRICE] = kelas.hargaKelas
            it[SELECTED] = kelas.isSelected
        }
    }

    fun getKelas(): Flow<KelasPenumpang> {
        return dataStore.data.map {
            KelasPenumpang(
                it[NAME] ?: "Kelas 1",
                it[PRICE] ?: 0,
                it[SELECTED] ?: false
            )
        }
    }

    suspend fun clearData(){
        dataStore.edit {
            it.clear()
        }
    }

    companion object{
        private val NAME = stringPreferencesKey("name")
        private val PRICE = intPreferencesKey("price")
        private val SELECTED = booleanPreferencesKey("selected")
    }
}