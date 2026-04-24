package io.github.semenciuccosmin.preferences.sample.ui.viewmodel.model

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

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
            CLEAR;
        }

        enum class Status {
            RUNNING,
            PASSED,
            FAILED
        }
    }
}
