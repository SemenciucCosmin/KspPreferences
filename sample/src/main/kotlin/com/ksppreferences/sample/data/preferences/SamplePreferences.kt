package com.ksppreferences.sample.data.preferences

import com.ksppreferences.annotations.BooleanPreference
import com.ksppreferences.annotations.Clear
import com.ksppreferences.annotations.Get
import com.ksppreferences.annotations.GetFlow
import com.ksppreferences.annotations.IntPreference
import com.ksppreferences.annotations.Preferences
import com.ksppreferences.annotations.Set
import com.ksppreferences.annotations.StringPreference
import kotlinx.coroutines.flow.Flow

@Preferences(name = "SAMPLE_PREFERENCES")
interface SamplePreferences {

    // ------------------------------ BOOLEAN ------------------------------
    @Get
    @BooleanPreference(key = KEY_BOOLEAN, defaultValue = false)
    suspend fun getBoolean(): Boolean

    @GetFlow
    @BooleanPreference(key = KEY_BOOLEAN, defaultValue = false)
    fun getBooleanFlow(): Flow<Boolean>

    @Set
    @BooleanPreference(key = KEY_BOOLEAN, defaultValue = false)
    suspend fun setBoolean(value: Boolean)

    // ------------------------------ Int ------------------------------
    @Get
    @IntPreference(key = KEY_INT, defaultValue = 0)
    suspend fun getInt(): Int

    @GetFlow
    @IntPreference(key = KEY_INT, defaultValue = 0)
    fun getIntFlow(): Flow<Int>

    @Set
    @IntPreference(key = KEY_INT, defaultValue = 0)
    suspend fun setInt(value: Int)

    // ------------------------------ STRING ------------------------------
    @Get
    @StringPreference(key = KEY_STRING, defaultValue = "")
    suspend fun getString(): String

    @GetFlow
    @StringPreference(key = KEY_STRING, defaultValue = "")
    fun getStringFlow(): Flow<String>

    @Set
    @StringPreference(key = KEY_STRING, defaultValue = "")
    suspend fun setString(value: String)

    @Clear
    suspend fun clear()

    companion object {
        private const val KEY_BOOLEAN = "KEY_BOOLEAN"
        private const val KEY_INT = "KEY_INT"
        private const val KEY_STRING = "KEY_STRING"
    }
}