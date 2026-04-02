package com.ksp.preferences.sample.data.preferences

import com.ksp.preferences.annotations.BooleanPreference
import com.ksp.preferences.annotations.ByteArrayPreference
import com.ksp.preferences.annotations.Clear
import com.ksp.preferences.annotations.DoublePreference
import com.ksp.preferences.annotations.FloatPreference
import com.ksp.preferences.annotations.Get
import com.ksp.preferences.annotations.GetFlow
import com.ksp.preferences.annotations.IntPreference
import com.ksp.preferences.annotations.LongPreference
import com.ksp.preferences.annotations.Preferences
import com.ksp.preferences.annotations.Set
import com.ksp.preferences.annotations.StringPreference
import com.ksp.preferences.sample.data.preferences.SamplePreferences.Companion.PREFERENCES_NAME
import kotlinx.coroutines.flow.Flow

/**
 * Sample DataStore preferences interface demonstrating all supported value types.
 *
 * Annotated with [@Preferences][Preferences] so the KSP processor generates a concrete
 * [SamplePreferencesImpl] backed by Jetpack DataStore. Each supported primitive type is
 * represented with a [Get] / [GetFlow] / [Set] triple so that both one-shot reads and
 * reactive `Flow`-based reads are covered.
 *
 * Default values are exposed as public constants on the [Companion] so that test
 * assertions can reference them without hard-coding magic literals.
 */
@Preferences(name = PREFERENCES_NAME)
interface SamplePreferences {

    // ------------------------------ BOOLEAN ------------------------------

    /** Reads the current [Boolean] preference value. Returns [DEFAULT_BOOLEAN] when unset. */
    @Get
    @BooleanPreference(key = KEY_BOOLEAN, defaultValue = DEFAULT_BOOLEAN)
    suspend fun getBoolean(): Boolean

    /** Returns a [Flow] that emits the [Boolean] preference on every change. */
    @GetFlow
    @BooleanPreference(key = KEY_BOOLEAN, defaultValue = DEFAULT_BOOLEAN)
    fun getBooleanFlow(): Flow<Boolean>

    /** Persists [value] as the [Boolean] preference. */
    @Set
    @BooleanPreference(key = KEY_BOOLEAN, defaultValue = DEFAULT_BOOLEAN)
    suspend fun setBoolean(value: Boolean)

    // ------------------------------ BYTE ARRAY ------------------------------

    /** Reads the current [ByteArray] preference value. Returns an empty array when unset. */
    @Get
    @ByteArrayPreference(key = KEY_BYTE_ARRAY, defaultValue = [])
    suspend fun getByteArray(): ByteArray

    /** Returns a [Flow] that emits the [ByteArray] preference on every change. */
    @GetFlow
    @ByteArrayPreference(key = KEY_BYTE_ARRAY, defaultValue = [])
    fun getByteArrayFlow(): Flow<ByteArray>

    /** Persists [value] as the [ByteArray] preference. */
    @Set
    @ByteArrayPreference(key = KEY_BYTE_ARRAY, defaultValue = [])
    suspend fun setByteArray(value: ByteArray)

    // ------------------------------ DOUBLE ------------------------------

    /** Reads the current [Double] preference value. Returns [DEFAULT_DOUBLE] when unset. */
    @Get
    @DoublePreference(key = KEY_DOUBLE, defaultValue = DEFAULT_DOUBLE)
    suspend fun getDouble(): Double

    /** Returns a [Flow] that emits the [Double] preference on every change. */
    @GetFlow
    @DoublePreference(key = KEY_DOUBLE, defaultValue = DEFAULT_DOUBLE)
    fun getDoubleFlow(): Flow<Double>

    /** Persists [value] as the [Double] preference. */
    @Set
    @DoublePreference(key = KEY_DOUBLE, defaultValue = DEFAULT_DOUBLE)
    suspend fun setDouble(value: Double)

    // ------------------------------ FLOAT ------------------------------

    /** Reads the current [Float] preference value. Returns [DEFAULT_FLOAT] when unset. */
    @Get
    @FloatPreference(key = KEY_FLOAT, defaultValue = DEFAULT_FLOAT)
    suspend fun getFloat(): Float

    /** Returns a [Flow] that emits the [Float] preference on every change. */
    @GetFlow
    @FloatPreference(key = KEY_FLOAT, defaultValue = DEFAULT_FLOAT)
    fun getFloatFlow(): Flow<Float>

    /** Persists [value] as the [Float] preference. */
    @Set
    @FloatPreference(key = KEY_FLOAT, defaultValue = DEFAULT_FLOAT)
    suspend fun setFloat(value: Float)

    // ------------------------------ INT ------------------------------

    /** Reads the current [Int] preference value. Returns [DEFAULT_INT] when unset. */
    @Get
    @IntPreference(key = KEY_INT, defaultValue = DEFAULT_INT)
    suspend fun getInt(): Int

    /** Returns a [Flow] that emits the [Int] preference on every change. */
    @GetFlow
    @IntPreference(key = KEY_INT, defaultValue = DEFAULT_INT)
    fun getIntFlow(): Flow<Int>

    /** Persists [value] as the [Int] preference. */
    @Set
    @IntPreference(key = KEY_INT, defaultValue = DEFAULT_INT)
    suspend fun setInt(value: Int)

    // ------------------------------ LONG ------------------------------

    /** Reads the current [Long] preference value. Returns [DEFAULT_LONG] when unset. */
    @Get
    @LongPreference(key = KEY_LONG, defaultValue = DEFAULT_LONG)
    suspend fun getLong(): Long

    /** Returns a [Flow] that emits the [Long] preference on every change. */
    @GetFlow
    @LongPreference(key = KEY_LONG, defaultValue = DEFAULT_LONG)
    fun getLongFlow(): Flow<Long>

    /** Persists [value] as the [Long] preference. */
    @Set
    @LongPreference(key = KEY_LONG, defaultValue = DEFAULT_LONG)
    suspend fun setLong(value: Long)

    // ------------------------------ STRING ------------------------------

    /** Reads the current [String] preference value. Returns [DEFAULT_STRING] when unset. */
    @Get
    @StringPreference(key = KEY_STRING, defaultValue = DEFAULT_STRING)
    suspend fun getString(): String

    /** Returns a [Flow] that emits the [String] preference on every change. */
    @GetFlow
    @StringPreference(key = KEY_STRING, defaultValue = DEFAULT_STRING)
    fun getStringFlow(): Flow<String>

    /** Persists [value] as the [String] preference. */
    @Set
    @StringPreference(key = KEY_STRING, defaultValue = DEFAULT_STRING)
    suspend fun setString(value: String)

    /** Removes all stored preference values, resetting everything to their defaults. */
    @Clear
    suspend fun clear()

    companion object {
        /** The DataStore file name used by the generated implementation. */
        private const val PREFERENCES_NAME = "SAMPLE_PREFERENCES"

        private const val KEY_BOOLEAN = "KEY_BOOLEAN"
        private const val KEY_BYTE_ARRAY = "KEY_BYTE_ARRAY"
        private const val KEY_DOUBLE = "KEY_DOUBLE"
        private const val KEY_FLOAT = "KEY_FLOAT"
        private const val KEY_INT = "KEY_INT"
        private const val KEY_LONG = "KEY_LONG"
        private const val KEY_STRING = "KEY_STRING"

        /** Default value returned by [getBoolean] when the key has not been written. */
        const val DEFAULT_BOOLEAN = false
        /** Default value returned by [getDouble] when the key has not been written. */
        const val DEFAULT_DOUBLE = 0.0
        /** Default value returned by [getFloat] when the key has not been written. */
        const val DEFAULT_FLOAT = 0f
        /** Default value returned by [getInt] when the key has not been written. */
        const val DEFAULT_INT = 0
        /** Default value returned by [getLong] when the key has not been written. */
        const val DEFAULT_LONG = 0L
        /** Default value returned by [getString] when the key has not been written. */
        const val DEFAULT_STRING = ""
    }
}
