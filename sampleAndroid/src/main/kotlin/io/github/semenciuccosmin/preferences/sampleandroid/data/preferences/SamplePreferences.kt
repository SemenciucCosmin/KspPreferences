package io.github.semenciuccosmin.preferences.sampleandroid.data.preferences

import io.github.semenciuccosmin.preferences.annotations.BooleanPreference
import io.github.semenciuccosmin.preferences.annotations.Clear
import io.github.semenciuccosmin.preferences.annotations.DoublePreference
import io.github.semenciuccosmin.preferences.annotations.FloatPreference
import io.github.semenciuccosmin.preferences.annotations.Get
import io.github.semenciuccosmin.preferences.annotations.GetFlow
import io.github.semenciuccosmin.preferences.annotations.IntPreference
import io.github.semenciuccosmin.preferences.annotations.LongPreference
import io.github.semenciuccosmin.preferences.annotations.ObjectPreference
import io.github.semenciuccosmin.preferences.annotations.Preferences
import io.github.semenciuccosmin.preferences.annotations.Set
import io.github.semenciuccosmin.preferences.annotations.StringPreference
import io.github.semenciuccosmin.preferences.sampleandroid.data.model.SampleObject
import io.github.semenciuccosmin.preferences.sampleandroid.data.preferences.SamplePreferences.Companion.PREFERENCES_NAME
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
@Suppress("TooManyFunctions")
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

    // ------------------------------ OBJECT ------------------------------

    @Get
    @ObjectPreference(key = KEY_OBJECT, clazz = SampleObject::class)
    suspend fun getObject(): SampleObject?

    @GetFlow
    @ObjectPreference(key = KEY_OBJECT, clazz = SampleObject::class)
    fun getObjectFlow(): Flow<SampleObject?>

    @Set
    @ObjectPreference(key = KEY_OBJECT, clazz = SampleObject::class)
    suspend fun setObject(value: SampleObject)

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
        private const val KEY_DOUBLE = "KEY_DOUBLE"
        private const val KEY_FLOAT = "KEY_FLOAT"
        private const val KEY_INT = "KEY_INT"
        private const val KEY_LONG = "KEY_LONG"
        private const val KEY_OBJECT = "KEY_OBJECT"
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
