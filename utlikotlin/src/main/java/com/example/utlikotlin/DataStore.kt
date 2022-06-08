package com.example.utlikotlin

import android.content.Context
import androidx.datastore.preferences.core.*
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

    /*Boolean*/
    suspend fun saveBoolean(context: Context, keyResId: Int, value: Boolean) {
        val key = booleanPreferencesKey(context.getString(keyResId))

        context.settings.edit {
            it[key] = value
        }
    }

    fun getBooleanFlow(context: Context, keyResId: Int, defaultValueResId: Int): Flow<Boolean> {
        val key = booleanPreferencesKey(context.getString(keyResId))
        val defaultValue = context.resources.getBoolean(defaultValueResId)

        return context.settings.data.map {
            it[key] ?: defaultValue
        }
    }

    fun getBoolean(context: Context, keyResId: Int, defaultValueResId: Int): Boolean {
        val key = booleanPreferencesKey(context.getString(keyResId))
        val defaultValue = context.resources.getBoolean(defaultValueResId)

        return runBlocking {
            context.settings.data.first()[key] ?: defaultValue
        }
    }

    /*Int*/
    suspend fun saveInt(context: Context, keyResId: Int, value: Int) {
        val key = intPreferencesKey(context.getString(keyResId))

        context.settings.edit {
            it[key] = value
        }
    }

    fun getIntFlow(context: Context, keyResId: Int, defaultValueResId: Int): Flow<Int> {
        val key = intPreferencesKey(context.getString(keyResId))
        val defaultValue = context.resources.getInteger(defaultValueResId)

        return context.settings.data.map {
            it[key] ?: defaultValue
        }
    }

    fun getInt(context: Context, keyResId: Int, defaultValueResId: Int): Int {
        val key = intPreferencesKey(context.getString(keyResId))
        val defaultValue = context.resources.getInteger(defaultValueResId)

        return runBlocking {
            context.settings.data.first()[key] ?: defaultValue
        }
    }

    /*Long*/
    suspend fun saveLong(context: Context, keyResId: Int, value: Long) {
        val key = longPreferencesKey(context.getString(keyResId))

        context.settings.edit {
            it[key] = value
        }
    }

    fun getLongFlow(context: Context, keyResId: Int, defaultValueResId: Int): Flow<Long> {
        val key = longPreferencesKey(context.getString(keyResId))
        val defaultValue = context.resources.getInteger(defaultValueResId).toLong()

        return context.settings.data.map {
            it[key] ?: defaultValue
        }
    }

    fun getLong(context: Context, keyResId: Int, defaultValueResId: Int): Long {
        val key = longPreferencesKey(context.getString(keyResId))
        val defaultValue = context.resources.getInteger(defaultValueResId).toLong()

        return runBlocking {
            context.settings.data.first()[key] ?: defaultValue
        }
    }
}