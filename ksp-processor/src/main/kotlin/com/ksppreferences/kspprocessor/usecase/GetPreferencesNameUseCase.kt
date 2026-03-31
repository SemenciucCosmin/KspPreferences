package com.ksppreferences.kspprocessor.usecase

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.ksppreferences.annotations.Preferences

internal class GetPreferencesNameUseCase {

    @OptIn(KspExperimental::class)
    operator fun invoke(interfaceDeclaration: KSClassDeclaration): String {
        val annotation = interfaceDeclaration.annotations.firstOrNull {
            it.shortName.asString() == Preferences::class.simpleName
        } ?: return PREFERENCES_DEFAULT_NAME

        return annotation.arguments.firstOrNull {
            it.name?.asString() == PREFERENCES_NAME
        }?.value as? String ?: PREFERENCES_DEFAULT_NAME
    }

    companion object {
        private const val PREFERENCES_NAME = "name"
        private const val PREFERENCES_DEFAULT_NAME = "PREFERENCES_DEFAULT_NAME"
    }
}
