package io.github.semenciuccosmin.preferences

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import io.github.semenciuccosmin.preferences.di.samplePreferencesTest
import io.github.semenciuccosmin.preferences.sample.data.preferences.SamplePreferences
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertEquals

/**
 * Instrumented tests for [SamplePreferences], covering every value type and operation
 * (one-shot get, reactive `Flow` get, set, and clear).
 *
 * ## Lifecycle design
 * Koin is started **once per test class** (in [beforeClass]) and stopped in [afterClass].
 * This keeps the [SamplePreferences] singleton alive across all test methods, ensuring
 * a single [androidx.datastore.preferences.core.Preferences] DataStore file is open at a
 * time and avoiding the "multiple DataStores active for the same file" error.
 *
 * [setUp] clears all stored values before each test method so that tests remain
 * independent of one another without needing to recreate the Koin container.
 */
@RunWith(AndroidJUnit4::class)
class SamplePreferencesTest : KoinTest {

    private val samplePreferences: SamplePreferences by inject()

    /**
     * Resets the DataStore state before each test so that every test starts from a clean
     * slate without restarting the Koin container.
     */
    @Before
    fun setUp() {
        runBlocking { samplePreferences.clear() }
    }

    /**
     * Verifies that [SamplePreferences.getBoolean] / [SamplePreferences.setBoolean] /
     * [SamplePreferences.getBooleanFlow] round-trip correctly.
     */
    @Test
    fun testBoolean() = runTest {
        assertEquals(
            expected = SamplePreferences.DEFAULT_BOOLEAN,
            actual = samplePreferences.getBoolean()
        )

        samplePreferences.setBoolean(true)

        assertEquals(
            expected = true,
            actual = samplePreferences.getBoolean()
        )

        samplePreferences.clear()

        samplePreferences.getBooleanFlow().test {
            assertEquals(
                expected = SamplePreferences.DEFAULT_BOOLEAN,
                actual = awaitItem()
            )

            samplePreferences.setBoolean(true)

            assertEquals(
                expected = true,
                actual = awaitItem()
            )
        }
    }

    /**
     * Verifies that [SamplePreferences.getByteArray] / [SamplePreferences.setByteArray] /
     * [SamplePreferences.getByteArrayFlow] round-trip correctly.
     */
    @Test
    fun testByeArray() = runTest {
        assertEquals(
            expected = byteArrayOf().toList(),
            actual = samplePreferences.getByteArray().toList()
        )

        samplePreferences.setByteArray(byteArrayOf(1, 2, 3))

        assertEquals(
            expected = byteArrayOf(1, 2, 3).toList(),
            actual = samplePreferences.getByteArray().toList()
        )

        samplePreferences.clear()

        samplePreferences.getByteArrayFlow().test {
            assertEquals(
                expected = byteArrayOf().toList(),
                actual = awaitItem().toList()
            )

            samplePreferences.setByteArray(byteArrayOf(1, 2, 3))

            assertEquals(
                expected = byteArrayOf(1, 2, 3).toList(),
                actual = awaitItem().toList()
            )
        }
    }

    /**
     * Verifies that [SamplePreferences.getDouble] / [SamplePreferences.setDouble] /
     * [SamplePreferences.getDoubleFlow] round-trip correctly.
     */
    @Test
    fun testDouble() = runTest {
        assertEquals(
            expected = SamplePreferences.DEFAULT_DOUBLE,
            actual = samplePreferences.getDouble()
        )

        samplePreferences.setDouble(1.0)

        assertEquals(
            expected = 1.0,
            actual = samplePreferences.getDouble()
        )

        samplePreferences.clear()

        samplePreferences.getDoubleFlow().test {
            assertEquals(
                expected = SamplePreferences.DEFAULT_DOUBLE,
                actual = awaitItem()
            )

            samplePreferences.setDouble(1.0)

            assertEquals(
                expected = 1.0,
                actual = awaitItem()
            )
        }
    }

    /**
     * Verifies that [SamplePreferences.getFloat] / [SamplePreferences.setFloat] /
     * [SamplePreferences.getFloatFlow] round-trip correctly.
     */
    @Test
    fun testFloat() = runTest {
        assertEquals(
            expected = SamplePreferences.DEFAULT_FLOAT,
            actual = samplePreferences.getFloat()
        )

        samplePreferences.setFloat(1f)

        assertEquals(
            expected = 1f,
            actual = samplePreferences.getFloat()
        )

        samplePreferences.clear()

        samplePreferences.getFloatFlow().test {
            assertEquals(
                expected = SamplePreferences.DEFAULT_FLOAT,
                actual = awaitItem()
            )

            samplePreferences.setFloat(1f)

            assertEquals(
                expected = 1f,
                actual = awaitItem()
            )
        }
    }

    /**
     * Verifies that [SamplePreferences.getInt] / [SamplePreferences.setInt] /
     * [SamplePreferences.getIntFlow] round-trip correctly.
     */
    @Test
    fun testInt() = runTest {
        assertEquals(
            expected = SamplePreferences.DEFAULT_INT,
            actual = samplePreferences.getInt()
        )

        samplePreferences.setInt(1)

        assertEquals(
            expected = 1,
            actual = samplePreferences.getInt()
        )

        samplePreferences.clear()

        samplePreferences.getIntFlow().test {
            assertEquals(
                expected = SamplePreferences.DEFAULT_INT,
                actual = awaitItem()
            )

            samplePreferences.setInt(1)

            assertEquals(
                expected = 1,
                actual = awaitItem()
            )
        }
    }

    /**
     * Verifies that [SamplePreferences.getLong] / [SamplePreferences.setLong] /
     * [SamplePreferences.getLongFlow] round-trip correctly.
     */
    @Test
    fun testLong() = runTest {
        assertEquals(
            expected = SamplePreferences.DEFAULT_LONG,
            actual = samplePreferences.getLong()
        )

        samplePreferences.setLong(1L)

        assertEquals(
            expected = 1L,
            actual = samplePreferences.getLong()
        )

        samplePreferences.clear()

        samplePreferences.getLongFlow().test {
            assertEquals(
                expected = SamplePreferences.DEFAULT_LONG,
                actual = awaitItem()
            )

            samplePreferences.setLong(1L)

            assertEquals(
                expected = 1L,
                actual = awaitItem()
            )
        }
    }

    /**
     * Verifies that [SamplePreferences.getString] / [SamplePreferences.setString] /
     * [SamplePreferences.getStringFlow] round-trip correctly.
     */
    @Test
    fun testString() = runTest {
        assertEquals(
            expected = SamplePreferences.DEFAULT_STRING,
            actual = samplePreferences.getString()
        )

        samplePreferences.setString("string")

        assertEquals(
            expected = "string",
            actual = samplePreferences.getString()
        )

        samplePreferences.clear()

        samplePreferences.getStringFlow().test {
            assertEquals(
                expected = SamplePreferences.DEFAULT_STRING,
                actual = awaitItem()
            )

            samplePreferences.setString("string")

            assertEquals(
                expected = "string",
                actual = awaitItem()
            )
        }
    }

    /**
     * Verifies that [SamplePreferences.clear] resets every value type back to its declared
     * default after values have been explicitly written.
     */
    @Test
    fun testClear() = runTest {
        assertEquals(
            expected = SamplePreferences.DEFAULT_BOOLEAN,
            actual = samplePreferences.getBoolean()
        )

        assertEquals(
            expected = byteArrayOf().toList(),
            actual = samplePreferences.getByteArray().toList()
        )

        assertEquals(
            expected = SamplePreferences.DEFAULT_DOUBLE,
            actual = samplePreferences.getDouble()
        )

        assertEquals(
            expected = SamplePreferences.DEFAULT_FLOAT,
            actual = samplePreferences.getFloat()
        )

        assertEquals(
            expected = SamplePreferences.DEFAULT_INT,
            actual = samplePreferences.getInt()
        )

        assertEquals(
            expected = SamplePreferences.DEFAULT_LONG,
            actual = samplePreferences.getLong()
        )

        assertEquals(
            expected = SamplePreferences.DEFAULT_STRING,
            actual = samplePreferences.getString()
        )

        samplePreferences.setBoolean(true)
        samplePreferences.setByteArray(byteArrayOf(1, 2, 3))
        samplePreferences.setDouble(1.0)
        samplePreferences.setFloat(1f)
        samplePreferences.setInt(1)
        samplePreferences.setLong(1L)
        samplePreferences.setString("string")

        samplePreferences.clear()

        assertEquals(
            expected = SamplePreferences.DEFAULT_BOOLEAN,
            actual = samplePreferences.getBoolean()
        )

        assertEquals(
            expected = byteArrayOf().toList(),
            actual = samplePreferences.getByteArray().toList()
        )

        assertEquals(
            expected = SamplePreferences.DEFAULT_DOUBLE,
            actual = samplePreferences.getDouble()
        )

        assertEquals(
            expected = SamplePreferences.DEFAULT_FLOAT,
            actual = samplePreferences.getFloat()
        )

        assertEquals(
            expected = SamplePreferences.DEFAULT_INT,
            actual = samplePreferences.getInt()
        )

        assertEquals(
            expected = SamplePreferences.DEFAULT_LONG,
            actual = samplePreferences.getLong()
        )

        assertEquals(
            expected = SamplePreferences.DEFAULT_STRING,
            actual = samplePreferences.getString()
        )
    }

    companion object {

        /**
         * Starts the Koin container once for the entire test class, registering
         * [SamplePreferences] as a singleton backed by the instrumentation [android.app.Application]
         * context.
         */
        @BeforeClass
        @JvmStatic
        fun beforeClass() {
            stopKoin()
            startKoin {
                androidContext(ApplicationProvider.getApplicationContext())
                modules(samplePreferencesTest())
            }
        }

        /**
         * Stops the Koin container after all test methods in this class have run.
         */
        @AfterClass
        @JvmStatic
        fun afterClass() {
            stopKoin()
        }
    }
}
