package io.github.semenciuccosmin.preferences.sampleandroid.ui.viewmodel.model

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class SampleUiState(
    val tests: ImmutableList<Test> = persistentListOf()
) {
    data class Test(
        val type: Type,
        val status: Status,
    ) {
        enum class Type {
            BOOLEAN,
            DOUBLE,
            FLOAT,
            INT,
            LONG,
            OBJECT,
            STRING,
            CLEAR
        }

        enum class Status {
            RUNNING,
            PASSED,
            FAILED
        }
    }
}
