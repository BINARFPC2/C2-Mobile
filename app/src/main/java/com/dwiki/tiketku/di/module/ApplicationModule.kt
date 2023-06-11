package com.dwiki.tiketku.di.module

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.dwiki.tiketku.model.datastore.KelasManager
import com.dwiki.tiketku.model.datastore.PenumpangManager
import com.dwiki.tiketku.model.datastore.PreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = {
                context.preferencesDataStoreFile("pref_manager")
            }
        )

    @Provides
    @Singleton
    fun providePrefManager(dataStore: DataStore<Preferences>): PreferenceManager {
        return PreferenceManager(dataStore)
    }

    @Provides
    @Singleton
    fun kelasPrefManager(dataStore: DataStore<Preferences>): KelasManager {
        return KelasManager(dataStore)
    }

    @Provides
    @Singleton
    fun penumpangPrefManager(dataStore: DataStore<Preferences>): PenumpangManager{
        return PenumpangManager(dataStore)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("preferences", Context.MODE_PRIVATE)
    }


}