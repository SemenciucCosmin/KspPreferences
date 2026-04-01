package com.ksppreferences.kspprocessor.usecase

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.ksppreferences.annotations.Preferences
import com.ksppreferences.kspprocessor.logger.Logger

internal class GetPreferencesNameUseCase(
    private val logger: Logger
) {

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
