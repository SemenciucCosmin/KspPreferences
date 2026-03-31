package com.ksppreferences.sample.data.preferences

import com.ksppreferences.annotations.BooleanPreference
import com.ksppreferences.annotations.Get
import com.ksppreferences.annotations.GetFlow
import com.ksppreferences.annotations.Preferences
import com.ksppreferences.annotations.Set
import kotlinx.coroutines.flow.Flow

@Preferences
interface TestPreferences {

    @GetFlow
    @BooleanPreference(key = "boolean1", defaultValue = false)
    fun getBoolean1(): Flow<String>

    @Set
    @BooleanPreference(key = "boolean1", defaultValue = false)
    fun setBoolean1()
}