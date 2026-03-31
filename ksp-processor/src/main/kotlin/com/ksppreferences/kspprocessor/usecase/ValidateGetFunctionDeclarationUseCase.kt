package com.ksppreferences.kspprocessor.usecase

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.ksppreferences.annotations.BooleanPreference
import com.ksppreferences.annotations.BytePreference
import com.ksppreferences.annotations.CharPreference
import com.ksppreferences.annotations.DoublePreference
import com.ksppreferences.annotations.FloatPreference
import com.ksppreferences.annotations.Get
import com.ksppreferences.annotations.IntPreference
import com.ksppreferences.annotations.LongPreference
import com.ksppreferences.annotations.ShortPreference
import com.ksppreferences.annotations.StringPreference
import com.ksppreferences.kspprocessor.annotations.ValueTypeAnnotations
import com.ksppreferences.kspprocessor.extension.ifNot
import com.ksppreferences.kspprocessor.extension.isTypeOf
import com.ksppreferences.kspprocessor.logger.Logger

internal class ValidateGetFunctionDeclarationUseCase(
    private val logger: Logger,
) {

    @OptIn(KspExperimental::class)
    operator fun invoke(function: KSFunctionDeclaration): Boolean {
        val functionName = function.simpleName.asString()
        val valueTypeAnnotation = ValueTypeAnnotations.all.firstOrNull {
            function.isAnnotationPresent(it)
        }

        return when (valueTypeAnnotation) {
            BooleanPreference::class -> function.returnType.isTypeOf(Boolean::class).ifNot {
                logger.logMismatchedReturnTypeError(
                    functionName = functionName,
                    accessorAnnotation = Get::class.simpleName ?: return@ifNot,
                    valueTypeAnnotation = valueTypeAnnotation::class.simpleName ?: return@ifNot,
                    expectedReturnType = Boolean::class.simpleName ?: return@ifNot
                )
            }

            BytePreference::class -> function.returnType.isTypeOf(Byte::class).ifNot {
                logger.logMismatchedReturnTypeError(
                    functionName = functionName,
                    accessorAnnotation = Get::class.simpleName ?: return@ifNot,
                    valueTypeAnnotation = valueTypeAnnotation::class.simpleName ?: return@ifNot,
                    expectedReturnType = Byte::class.simpleName ?: return@ifNot
                )
            }

            CharPreference::class -> function.returnType.isTypeOf(Char::class).ifNot {
                logger.logMismatchedReturnTypeError(
                    functionName = functionName,
                    accessorAnnotation = Get::class.simpleName ?: return@ifNot,
                    valueTypeAnnotation = valueTypeAnnotation::class.simpleName ?: return@ifNot,
                    expectedReturnType = Char::class.simpleName ?: return@ifNot
                )
            }

            DoublePreference::class -> function.returnType.isTypeOf(Double::class).ifNot {
                logger.logMismatchedReturnTypeError(
                    functionName = functionName,
                    accessorAnnotation = Get::class.simpleName ?: return@ifNot,
                    valueTypeAnnotation = valueTypeAnnotation::class.simpleName ?: return@ifNot,
                    expectedReturnType = Double::class.simpleName ?: return@ifNot
                )
            }

            FloatPreference::class -> function.returnType.isTypeOf(Float::class).ifNot {
                logger.logMismatchedReturnTypeError(
                    functionName = functionName,
                    accessorAnnotation = Get::class.simpleName ?: return@ifNot,
                    valueTypeAnnotation = valueTypeAnnotation::class.simpleName ?: return@ifNot,
                    expectedReturnType = Float::class.simpleName ?: return@ifNot
                )
            }

            IntPreference::class -> function.returnType.isTypeOf(Int::class).ifNot {
                logger.logMismatchedReturnTypeError(
                    functionName = functionName,
                    accessorAnnotation = Get::class.simpleName ?: return@ifNot,
                    valueTypeAnnotation = valueTypeAnnotation::class.simpleName ?: return@ifNot,
                    expectedReturnType = Int::class.simpleName ?: return@ifNot
                )
            }

            LongPreference::class -> function.returnType.isTypeOf(Long::class).ifNot {
                logger.logMismatchedReturnTypeError(
                    functionName = functionName,
                    accessorAnnotation = Get::class.simpleName ?: return@ifNot,
                    valueTypeAnnotation = valueTypeAnnotation::class.simpleName ?: return@ifNot,
                    expectedReturnType = Long::class.simpleName ?: return@ifNot
                )
            }

            ShortPreference::class -> function.returnType.isTypeOf(Short::class).ifNot {
                logger.logMismatchedReturnTypeError(
                    functionName = functionName,
                    accessorAnnotation = Get::class.simpleName ?: return@ifNot,
                    valueTypeAnnotation = valueTypeAnnotation::class.simpleName ?: return@ifNot,
                    expectedReturnType = Short::class.simpleName ?: return@ifNot
                )
            }

            StringPreference::class -> function.returnType.isTypeOf(String::class).ifNot {
                logger.logMismatchedReturnTypeError(
                    functionName = functionName,
                    accessorAnnotation = Get::class.simpleName ?: return@ifNot,
                    valueTypeAnnotation = valueTypeAnnotation::class.simpleName ?: return@ifNot,
                    expectedReturnType = String::class.simpleName ?: return@ifNot
                )
            }

            else -> {
                logger.logMissingAnnotationError(functionName, ValueTypeAnnotations.allString)
                false
            }
        }
    }
}
