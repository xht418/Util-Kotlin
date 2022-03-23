package com.example.utlikotlin

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

object DataStore {
    private val Context.settings by preferencesDataStore("settings")

    /*String*/
    suspend fun saveString(context: Context, keyResId: Int, value: String) {
        val key = stringPreferencesKey(context.getString(keyResId))

        context.settings.edit {
            it[key] = value
        }
    }

    fun getStringFlow(context: Context, keyResId: Int, defaultValueResId: Int): Flow<String> {
        val key = stringPreferencesKey(context.getString(keyResId))
        val defaultValue = context.getString(defaultValueResId)

        return context.settings.data.map {
            it[key] ?: defaultValue
        }
    }

    fun getString(context: Context, keyResId: Int, defaultValueResId: Int): String {
        val key = stringPreferencesKey(context.getString(keyResId))
        val defaultValue = context.getString(defaultValueResId)

        return runBlocking {
            context.settings.data.first()[key] ?: defaultValue
        }
    }
}