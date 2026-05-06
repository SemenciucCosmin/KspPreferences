package io.github.semenciuccosmin.preferences.sampleandroid.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.semenciuccosmin.preferences.sampleandroid.data.model.SampleObject
import io.github.semenciuccosmin.preferences.sampleandroid.data.preferences.SamplePreferences
import io.github.semenciuccosmin.preferences.sampleandroid.ui.viewmodel.model.SampleUiState
import io.github.semenciuccosmin.preferences.sampleandroid.ui.viewmodel.model.SampleUiState.Test
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

@Suppress("TooManyFunctions")
class SampleViewModel(
    private val samplePreferences: SamplePreferences,
) : ViewModel() {

    private val _uiState: MutableStateFlow<SampleUiState> = MutableStateFlow(SampleUiState())
    val uiState = _uiState
        .asStateFlow()
        .onStart { startTests() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5.seconds.inWholeMilliseconds),
            initialValue = _uiState.value,
        )

    init {
        startTests()
    }

    private fun startTests() {
        val tests = getAllTests()
        _uiState.update { it.copy(tests = tests) }
        tests.forEach(::runTest)
    }

    private fun runTest(test: Test) {
        when (test.type) {
            Test.Type.BOOLEAN -> runBooleanTest()
            Test.Type.DOUBLE -> runDoubleTest()
            Test.Type.FLOAT -> runFloatTest()
            Test.Type.INT -> runIntTest()
            Test.Type.LONG -> runLongTest()
            Test.Type.OBJECT -> runObjectTest()
            Test.Type.STRING -> runStringTest()
            Test.Type.CLEAR -> runClearTest()
        }
    }

    private fun runBooleanTest() {
        viewModelScope.launch {
            if (samplePreferences.getBoolean() != SamplePreferences.DEFAULT_BOOLEAN) {
                setTestStatus(Test.Type.BOOLEAN, Test.Status.FAILED)
            }

            samplePreferences.setBoolean(true)

            if (samplePreferences.getBoolean() != true) {
                setTestStatus(Test.Type.BOOLEAN, Test.Status.FAILED)
            }

            samplePreferences.clear()

            setTestStatus(Test.Type.BOOLEAN, Test.Status.PASSED)
        }
    }

    private fun runDoubleTest() {
        viewModelScope.launch {
            if (samplePreferences.getDouble() != SamplePreferences.DEFAULT_DOUBLE) {
                setTestStatus(Test.Type.DOUBLE, Test.Status.FAILED)
            }

            samplePreferences.setDouble(1.0)

            if (samplePreferences.getDouble() != 1.0) {
                setTestStatus(Test.Type.DOUBLE, Test.Status.FAILED)
            }

            samplePreferences.clear()

            setTestStatus(Test.Type.DOUBLE, Test.Status.PASSED)
        }
    }

    private fun runFloatTest() {
        viewModelScope.launch {
            if (samplePreferences.getFloat() != SamplePreferences.DEFAULT_FLOAT) {
                setTestStatus(Test.Type.FLOAT, Test.Status.FAILED)
            }

            samplePreferences.setFloat(1f)

            if (samplePreferences.getFloat() != 1f) {
                setTestStatus(Test.Type.FLOAT, Test.Status.FAILED)
            }

            samplePreferences.clear()

            setTestStatus(Test.Type.FLOAT, Test.Status.PASSED)
        }
    }

    private fun runIntTest() {
        viewModelScope.launch {
            if (samplePreferences.getInt() != SamplePreferences.DEFAULT_INT) {
                setTestStatus(Test.Type.INT, Test.Status.FAILED)
            }

            samplePreferences.setInt(1)

            if (samplePreferences.getInt() != 1) {
                setTestStatus(Test.Type.INT, Test.Status.FAILED)
            }

            samplePreferences.clear()

            setTestStatus(Test.Type.INT, Test.Status.PASSED)
        }
    }

    private fun runLongTest() {
        viewModelScope.launch {
            if (samplePreferences.getLong() != SamplePreferences.DEFAULT_LONG) {
                setTestStatus(Test.Type.LONG, Test.Status.FAILED)
            }

            samplePreferences.setLong(1L)

            if (samplePreferences.getLong() != 1L) {
                setTestStatus(Test.Type.LONG, Test.Status.FAILED)
            }

            samplePreferences.clear()

            setTestStatus(Test.Type.LONG, Test.Status.PASSED)
        }
    }

    private fun runObjectTest() {
        viewModelScope.launch {
            if (samplePreferences.getObject() != null) {
                setTestStatus(Test.Type.OBJECT, Test.Status.FAILED)
            }

            val sampleObject = SampleObject(id = "sample_object_id", name = "sample_object_name")
            samplePreferences.setObject(sampleObject)

            if (samplePreferences.getObject() != sampleObject) {
                setTestStatus(Test.Type.OBJECT, Test.Status.FAILED)
            }

            samplePreferences.clear()

            setTestStatus(Test.Type.OBJECT, Test.Status.PASSED)
        }
    }

    private fun runStringTest() {
        viewModelScope.launch {
            if (samplePreferences.getString() != SamplePreferences.DEFAULT_STRING) {
                setTestStatus(Test.Type.STRING, Test.Status.FAILED)
            }

            samplePreferences.setString("string")

            if (samplePreferences.getString() != "string") {
                setTestStatus(Test.Type.STRING, Test.Status.FAILED)
            }

            samplePreferences.clear()

            setTestStatus(Test.Type.STRING, Test.Status.PASSED)
        }
    }

    private fun runClearTest() {
        viewModelScope.launch {
            val isAnyDefaultValueWrong = listOf(
                samplePreferences.getBoolean() != SamplePreferences.DEFAULT_BOOLEAN,
                samplePreferences.getDouble() != SamplePreferences.DEFAULT_DOUBLE,
                samplePreferences.getFloat() != SamplePreferences.DEFAULT_FLOAT,
                samplePreferences.getInt() != SamplePreferences.DEFAULT_INT,
                samplePreferences.getLong() != SamplePreferences.DEFAULT_LONG,
                samplePreferences.getObject() != null,
                samplePreferences.getString() != SamplePreferences.DEFAULT_STRING,
            ).any { it }

            if (isAnyDefaultValueWrong) {
                setTestStatus(Test.Type.CLEAR, Test.Status.FAILED)
            }

            samplePreferences.setBoolean(true)
            samplePreferences.setDouble(1.0)
            samplePreferences.setFloat(1f)
            samplePreferences.setInt(1)
            samplePreferences.setLong(1L)
            samplePreferences.setString("string")

            samplePreferences.clear()

            val isAnyClearedValueWrong = listOf(
                samplePreferences.getBoolean() != SamplePreferences.DEFAULT_BOOLEAN,
                samplePreferences.getDouble() != SamplePreferences.DEFAULT_DOUBLE,
                samplePreferences.getFloat() != SamplePreferences.DEFAULT_FLOAT,
                samplePreferences.getInt() != SamplePreferences.DEFAULT_INT,
                samplePreferences.getLong() != SamplePreferences.DEFAULT_LONG,
                samplePreferences.getObject() != null,
                samplePreferences.getString() != SamplePreferences.DEFAULT_STRING,
            ).any { it }

            if (isAnyClearedValueWrong) {
                setTestStatus(Test.Type.CLEAR, Test.Status.FAILED)
            } else {
                setTestStatus(Test.Type.CLEAR, Test.Status.PASSED)
            }
        }
    }

    private fun setTestStatus(testType: Test.Type, status: Test.Status) {
        _uiState.update { state ->
            val updatedTests = state.tests.map {
                when (it.type) {
                    testType -> it.copy(status = status)
                    else -> it
                }
            }.toImmutableList()

            state.copy(tests = updatedTests)
        }
    }

    private fun getAllTests() = Test.Type.entries.map { testType ->
        Test(
            type = testType,
            status = Test.Status.RUNNING
        )
    }.toImmutableList()
}
