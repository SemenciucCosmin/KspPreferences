package com.ksppreferences.sample.data.preferences

import com.ksppreferences.annotations.BooleanPreference
import com.ksppreferences.annotations.ByteArrayPreference
import com.ksppreferences.annotations.Clear
import com.ksppreferences.annotations.DoublePreference
import com.ksppreferences.annotations.FloatPreference
import com.ksppreferences.annotations.Get
import com.ksppreferences.annotations.GetFlow
import com.ksppreferences.annotations.IntPreference
import com.ksppreferences.annotations.LongPreference
import com.ksppreferences.annotations.Preferences
import com.ksppreferences.annotations.Set
import com.ksppreferences.annotations.StringPreference
import com.ksppreferences.sample.data.preferences.SamplePreferences.Companion.PREFERENCES_NAME
import kotlinx.coroutines.flow.Flow

@Preferences(name = PREFERENCES_NAME)
interface SamplePreferences {

    // ------------------------------ BOOLEAN ------------------------------
    @Get
    @BooleanPreference(key = KEY_BOOLEAN, defaultValue = DEFAULT_BOOLEAN)
    suspend fun getBoolean(): Boolean

    @GetFlow
    @BooleanPreference(key = KEY_BOOLEAN, defaultValue = DEFAULT_BOOLEAN)
    fun getBooleanFlow(): Flow<Boolean>

    @Set
    @BooleanPreference(key = KEY_BOOLEAN, defaultValue = DEFAULT_BOOLEAN)
    suspend fun setBoolean(value: Boolean)

    // ------------------------------ BYTE ARRAY ------------------------------
    @Get
    @ByteArrayPreference(key = KEY_BYTE_ARRAY, defaultValue = [])
    suspend fun getByteArray(): ByteArray

    @GetFlow
    @ByteArrayPreference(key = KEY_BYTE_ARRAY, defaultValue = [])
    fun getByteArrayFlow(): Flow<ByteArray>

    @Set
    @ByteArrayPreference(key = KEY_BYTE_ARRAY, defaultValue = [])
    suspend fun setByteArray(value: ByteArray)

    // ------------------------------ DOUBLE ------------------------------
    @Get
    @DoublePreference(key = KEY_DOUBLE, defaultValue = DEFAULT_DOUBLE)
    suspend fun getDouble(): Double

    @GetFlow
    @DoublePreference(key = KEY_DOUBLE, defaultValue = DEFAULT_DOUBLE)
    fun getDoubleFlow(): Flow<Double>

    @Set
    @DoublePreference(key = KEY_DOUBLE, defaultValue = DEFAULT_DOUBLE)
    suspend fun setDouble(value: Double)

    // ------------------------------ FLOAT ------------------------------
    @Get
    @FloatPreference(key = KEY_FLOAT, defaultValue = DEFAULT_FLOAT)
    suspend fun getFloat(): Float

    @GetFlow
    @FloatPreference(key = KEY_FLOAT, defaultValue = DEFAULT_FLOAT)
    fun getFloatFlow(): Flow<Float>

    @Set
    @FloatPreference(key = KEY_FLOAT, defaultValue = DEFAULT_FLOAT)
    suspend fun setFloat(value: Float)
    
    // ------------------------------ INT ------------------------------
    @Get
    @IntPreference(key = KEY_INT, defaultValue = DEFAULT_INT)
    suspend fun getInt(): Int

    @GetFlow
    @IntPreference(key = KEY_INT, defaultValue = DEFAULT_INT)
    fun getIntFlow(): Flow<Int>

    @Set
    @IntPreference(key = KEY_INT, defaultValue = DEFAULT_INT)
    suspend fun setInt(value: Int)

    // ------------------------------ LONG ------------------------------
    @Get
    @LongPreference(key = KEY_LONG, defaultValue = DEFAULT_LONG)
    suspend fun getLong(): Long

    @GetFlow
    @LongPreference(key = KEY_LONG, defaultValue = DEFAULT_LONG)
    fun getLongFlow(): Flow<Long>

    @Set
    @LongPreference(key = KEY_LONG, defaultValue = DEFAULT_LONG)
    suspend fun setLong(value: Long)

    // ------------------------------ STRING ------------------------------
    @Get
    @StringPreference(key = KEY_STRING, defaultValue = DEFAULT_STRING)
    suspend fun getString(): String

    @GetFlow
    @StringPreference(key = KEY_STRING, defaultValue = DEFAULT_STRING)
    fun getStringFlow(): Flow<String>

    @Set
    @StringPreference(key = KEY_STRING, defaultValue = DEFAULT_STRING)
    suspend fun setString(value: String)

    @Clear
    suspend fun clear()

    companion object {
        private const val PREFERENCES_NAME = "SAMPLE_PREFERENCES"
        private const val KEY_BOOLEAN = "KEY_BOOLEAN"
        private const val KEY_BYTE_ARRAY = "KEY_BYTE_ARRAY"
        private const val KEY_DOUBLE = "KEY_DOUBLE"
        private const val KEY_FLOAT = "KEY_FLOAT"
        private const val KEY_INT = "KEY_INT"
        private const val KEY_LONG = "KEY_LONG"
        private const val KEY_STRING = "KEY_STRING"

        const val DEFAULT_BOOLEAN = false
        const val DEFAULT_DOUBLE = 0.0
        const val DEFAULT_FLOAT = 0f
        const val DEFAULT_INT = 0
        const val DEFAULT_LONG = 0L
        const val DEFAULT_STRING = ""
    }
}
