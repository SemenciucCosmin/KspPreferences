package com.ksppreferences.kspprocessor.usecase

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getDeclaredFunctions
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.ksppreferences.annotations.BooleanPreference
import com.ksppreferences.annotations.ByteArrayPreference
import com.ksppreferences.annotations.DoublePreference
import com.ksppreferences.annotations.FloatPreference
import com.ksppreferences.annotations.IntPreference
import com.ksppreferences.annotations.LongPreference
import com.ksppreferences.annotations.StringPreference
import com.ksppreferences.kspprocessor.annotations.AccessorAnnotations
import com.ksppreferences.kspprocessor.annotations.ValueTypeAnnotations
import com.ksppreferences.kspprocessor.logger.Logger
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlin.sequences.forEach

internal class GenerateImportsUseCase {

    @OptIn(KspExperimental::class)
    operator fun invoke(interfaceDeclaration: KSClassDeclaration): String {
        val packageName = interfaceDeclaration.packageName.asString()
        val preferencesKeysImports = ValueTypeAnnotations.all.filter { annotation ->
            interfaceDeclaration.getAllFunctions().any { it.isAnnotationPresent(annotation) }
        }.mapNotNull { annotation ->
            when(annotation) {
                BooleanPreference::class -> BOOLEAN_PREFERENCES_KEY_NAME
                ByteArrayPreference::class -> BYTE_ARRAY_PREFERENCES_KEY_NAME
                DoublePreference::class -> DOUBLE_PREFERENCES_KEY_NAME
                FloatPreference::class -> FLOAT_PREFERENCES_KEY_NAME
                IntPreference::class -> INT_PREFERENCES_KEY_NAME
                LongPreference::class -> LONG_PREFERENCES_KEY_NAME
                StringPreference::class -> STRING_PREFERENCES_KEY_NAME
                else -> null
            }
        }

        return buildString {
            appendLine("package $packageName")
            appendLine()

            appendLine("import android.content.Context")
            appendLine("import androidx.datastore.core.DataStore")
            appendLine("import androidx.datastore.preferences.core.Preferences")
            preferencesKeysImports.forEach {
                appendLine("import androidx.datastore.preferences.core.$it")
            }
            appendLine("import androidx.datastore.preferences.core.edit")
            appendLine("import androidx.datastore.preferences.preferencesDataStore")
            appendLine("import kotlinx.coroutines.flow.Flow")
            appendLine("import kotlinx.coroutines.flow.firstOrNull")
            appendLine("import kotlinx.coroutines.flow.map")
        }
    }

    companion object {
        private const val BOOLEAN_PREFERENCES_KEY_NAME = "booleanPreferencesKey"
        private const val BYTE_ARRAY_PREFERENCES_KEY_NAME = "byteArrayPreferencesKey"
        private const val DOUBLE_PREFERENCES_KEY_NAME = "doublePreferencesKey"
        private const val FLOAT_PREFERENCES_KEY_NAME = "floatPreferencesKey"
        private const val INT_PREFERENCES_KEY_NAME = "intPreferencesKey"
        private const val LONG_PREFERENCES_KEY_NAME = "longPreferencesKey"
        private const val STRING_PREFERENCES_KEY_NAME = "stringPreferencesKey"
    }
}
