package io.github.semenciuccosmin.preferences.compiler.usecase

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.symbol.KSClassDeclaration
import io.github.semenciuccosmin.preferences.annotations.Preferences
import io.github.semenciuccosmin.preferences.compiler.logger.Logger

/**
 * Resolves the DataStore file name declared in the
 * [io.github.semenciuccosmin.preferences.annotations.Preferences] annotation of an interface.
 *
 * Falls back to [PREFERENCES_DEFAULT_NAME] and logs an error when the annotation or its
 * `name` argument cannot be read.
 */
internal class GetPreferencesNameUseCase(
    private val logger: Logger
) {

    /**
     * Returns the `name` parameter from the [@Preferences][Preferences] annotation on
     * [interfaceDeclaration], or [PREFERENCES_DEFAULT_NAME] if it cannot be resolved.
     *
     * @param interfaceDeclaration The KSP declaration of the annotated interface.
     * @return The DataStore file name string.
     */
    @OptIn(KspExperimental::class)
    operator fun invoke(interfaceDeclaration: KSClassDeclaration): String {
        val interfaceName = interfaceDeclaration.simpleName.asString()
        val annotation = interfaceDeclaration.annotations.firstOrNull {
            it.shortName.asString() == Preferences::class.simpleName
        }

        val name = annotation?.arguments?.firstOrNull {
            it.name?.asString() == PREFERENCES_NAME
        }?.value as? String

        if (name == null) {
            logger.logMisingPreferencesNameError(interfaceName)
            return PREFERENCES_DEFAULT_NAME
        }

        return name
    }

    companion object {
        private const val PREFERENCES_NAME = "name"
        private const val PREFERENCES_DEFAULT_NAME = "PREFERENCES_DEFAULT_NAME"
    }
}
