package io.github.semenciuccosmin.preferences.sample.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.semenciuccosmin.preferences.sample.data.preferences.SamplePreferences
import io.github.semenciuccosmin.preferences.sample.ui.viewmodel.model.SampleUiState
import io.github.semenciuccosmin.preferences.sample.ui.viewmodel.model.SampleUiState.Test
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

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

    }

    private fun runFloatTest() {

    }

    private fun runIntTest() {

    }

    private fun runLongTest() {

    }

    private fun runObjectTest() {

    }

    private fun runStringTest() {

    }

    private fun runClearTest() {

    }

    private fun setTestStatus(testType: Test.Type, status: Test.Status) {
        _uiState.update { state ->
            val updatedTests = state.tests.map {
                when(it.type) {
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
