package com.ksp.preferences.compiler.usecase

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.ksp.preferences.annotations.BooleanPreference
import com.ksp.preferences.annotations.ByteArrayPreference
import com.ksp.preferences.annotations.DoublePreference
import com.ksp.preferences.annotations.FloatPreference
import com.ksp.preferences.annotations.IntPreference
import com.ksp.preferences.annotations.LongPreference
import com.ksp.preferences.annotations.StringPreference
import com.ksp.preferences.compiler.annotations.ValueTypeAnnotations

/**
 * Generates the package declaration and import block for a DataStore preferences
 * implementation file.
 *
 * Only the `preferencesKey` factory functions that are actually needed by the annotated
 * interface are included (e.g. `booleanPreferencesKey` is omitted when the interface
 * declares no [BooleanPreference] functions). All other DataStore and coroutine imports
 * are always emitted.
 */
internal class GenerateImportsUseCase {

    /**
     * Produces the Kotlin source string containing the package statement and all required
     * import declarations for the given interface's implementation.
     *
     * @param interfaceDeclaration The KSP declaration of the annotated interface.
     * @return A Kotlin source string ending with a newline, ready to be prepended to the
     *         generated implementation file.
     */
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
