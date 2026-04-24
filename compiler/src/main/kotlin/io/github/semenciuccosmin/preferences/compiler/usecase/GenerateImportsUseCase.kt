package io.github.semenciuccosmin.preferences.compiler.usecase

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getDeclaredFunctions
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import io.github.semenciuccosmin.preferences.annotations.BooleanPreference
import io.github.semenciuccosmin.preferences.annotations.DoublePreference
import io.github.semenciuccosmin.preferences.annotations.FloatPreference
import io.github.semenciuccosmin.preferences.annotations.IntPreference
import io.github.semenciuccosmin.preferences.annotations.LongPreference
import io.github.semenciuccosmin.preferences.annotations.ObjectPreference
import io.github.semenciuccosmin.preferences.annotations.StringPreference
import io.github.semenciuccosmin.preferences.compiler.annotations.ValueTypeAnnotations

/**
 * Generates the package declaration and import block for a DataStore preferences
 * implementation file.
 *
 * Only the `preferencesKey` factory functions that are actually needed by the annotated
 * interface are included (e.g. `booleanPreferencesKey` is omitted when the interface
 * declares no [BooleanPreference] functions). All other DataStore and coroutine imports
 * are always emitted.
 */
internal class GenerateImportsUseCase(
    private val getValueTypeAnnotationData: GetValueTypeAnnotationData,
) {

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
        val allValueTypeAnnotations = ValueTypeAnnotations.all.filter { annotation ->
            interfaceDeclaration.getDeclaredFunctions().any { it.isAnnotationPresent(annotation) }
        }

        val objects = interfaceDeclaration.getDeclaredFunctions().filter {
            it.isAnnotationPresent(ObjectPreference::class)
        }.mapNotNull { getValueTypeAnnotationData(it).objectType }.toList().distinct()

        val preferencesKeysImports = allValueTypeAnnotations.mapNotNull { annotation ->
            when (annotation) {
                BooleanPreference::class -> BOOLEAN_PREFERENCES_KEY_NAME
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

            appendLine("import androidx.datastore.core.DataStore")
            appendLine("import androidx.datastore.preferences.core.Preferences")

            preferencesKeysImports.forEach {
                appendLine("import androidx.datastore.preferences.core.$it")
            }

            appendLine("import androidx.datastore.preferences.core.edit")
            appendLine("import io.github.semenciuccosmin.preferences.datastore.createDataStore")
            appendLine("import kotlinx.coroutines.flow.Flow")
            appendLine("import kotlinx.coroutines.flow.firstOrNull")
            appendLine("import kotlinx.coroutines.flow.map")

            if (ObjectPreference::class in allValueTypeAnnotations) {
                if (StringPreference::class !in allValueTypeAnnotations) {
                    appendLine("import androidx.datastore.preferences.core.stringPreferencesKey")
                }
                appendLine("import kotlinx.serialization.encodeToString")
                appendLine("import kotlinx.serialization.json.Json")
            }

            objects.forEach {
                val declaration = (it as? KSType)?.declaration
                val packageName = declaration?.packageName?.asString()
                val objectName = declaration?.simpleName?.asString()
                appendLine("import $packageName.$objectName")
            }
        }
    }

    companion object {
        private const val BOOLEAN_PREFERENCES_KEY_NAME = "booleanPreferencesKey"
        private const val DOUBLE_PREFERENCES_KEY_NAME = "doublePreferencesKey"
        private const val FLOAT_PREFERENCES_KEY_NAME = "floatPreferencesKey"
        private const val INT_PREFERENCES_KEY_NAME = "intPreferencesKey"
        private const val LONG_PREFERENCES_KEY_NAME = "longPreferencesKey"
        private const val STRING_PREFERENCES_KEY_NAME = "stringPreferencesKey"
    }
}
