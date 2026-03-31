package com.ksppreferences.kspprocessor.usecase

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.ksppreferences.annotations.BooleanPreference
import com.ksppreferences.annotations.BytePreference
import com.ksppreferences.annotations.CharPreference
import com.ksppreferences.annotations.DoublePreference
import com.ksppreferences.annotations.FloatPreference
import com.ksppreferences.annotations.GetFlow
import com.ksppreferences.annotations.IntPreference
import com.ksppreferences.annotations.LongPreference
import com.ksppreferences.annotations.ShortPreference
import com.ksppreferences.annotations.StringPreference
import com.ksppreferences.kspprocessor.annotations.ValueTypeAnnotations
import com.ksppreferences.kspprocessor.extension.ifNot
import com.ksppreferences.kspprocessor.extension.isFlowOf
import com.ksppreferences.kspprocessor.logger.Logger

internal class ValidateGetFlowFunctionDeclarationUseCase(
    private val logger: Logger,
) {

    @OptIn(KspExperimental::class)
    operator fun invoke(function: KSFunctionDeclaration): Boolean {
        val functionName = function.simpleName.asString()
        val valueTypeAnnotation = ValueTypeAnnotations.all.firstOrNull {
            function.isAnnotationPresent(it)
        }

        return when (valueTypeAnnotation) {
            BooleanPreference::class -> function.returnType.isFlowOf(Boolean::class).ifNot {
                logger.logMismatchedReturnTypeError(
                    functionName = functionName,
                    accessorAnnotation = GetFlow::class.simpleName ?: return@ifNot,
                    valueTypeAnnotation = valueTypeAnnotation::class.simpleName ?: return@ifNot,
                    expectedReturnType = "Flow<Boolean>",
                )
            }

            BytePreference::class -> function.returnType.isFlowOf(Byte::class).ifNot {
                logger.logMismatchedReturnTypeError(
                    functionName = functionName,
                    accessorAnnotation = GetFlow::class.simpleName ?: return@ifNot,
                    valueTypeAnnotation = valueTypeAnnotation::class.simpleName ?: return@ifNot,
                    expectedReturnType = "Flow<Byte>",
                )
            }

            CharPreference::class -> function.returnType.isFlowOf(Char::class).ifNot {
                logger.logMismatchedReturnTypeError(
                    functionName = functionName,
                    accessorAnnotation = GetFlow::class.simpleName ?: return@ifNot,
                    valueTypeAnnotation = valueTypeAnnotation::class.simpleName ?: return@ifNot,
                    expectedReturnType = "Flow<Char>",
                )
            }

            DoublePreference::class -> function.returnType.isFlowOf(Double::class).ifNot {
                logger.logMismatchedReturnTypeError(
                    functionName = functionName,
                    accessorAnnotation = GetFlow::class.simpleName ?: return@ifNot,
                    valueTypeAnnotation = valueTypeAnnotation::class.simpleName ?: return@ifNot,
                    expectedReturnType = "Flow<Double>",
                )
            }

            FloatPreference::class -> function.returnType.isFlowOf(Float::class).ifNot {
                logger.logMismatchedReturnTypeError(
                    functionName = functionName,
                    accessorAnnotation = GetFlow::class.simpleName ?: return@ifNot,
                    valueTypeAnnotation = valueTypeAnnotation::class.simpleName ?: return@ifNot,
                    expectedReturnType = "Flow<Float>",
                )
            }

            IntPreference::class -> function.returnType.isFlowOf(Int::class).ifNot {
                logger.logMismatchedReturnTypeError(
                    functionName = functionName,
                    accessorAnnotation = GetFlow::class.simpleName ?: return@ifNot,
                    valueTypeAnnotation = valueTypeAnnotation::class.simpleName ?: return@ifNot,
                    expectedReturnType = "Flow<Int>",
                )
            }

            LongPreference::class -> function.returnType.isFlowOf(Long::class).ifNot {
                logger.logMismatchedReturnTypeError(
                    functionName = functionName,
                    accessorAnnotation = GetFlow::class.simpleName ?: return@ifNot,
                    valueTypeAnnotation = valueTypeAnnotation::class.simpleName ?: return@ifNot,
                    expectedReturnType = "Flow<Long>",
                )
            }

            ShortPreference::class -> function.returnType.isFlowOf(Short::class).ifNot {
                logger.logMismatchedReturnTypeError(
                    functionName = functionName,
                    accessorAnnotation = GetFlow::class.simpleName ?: return@ifNot,
                    valueTypeAnnotation = valueTypeAnnotation::class.simpleName ?: return@ifNot,
                    expectedReturnType = "Flow<Short>",
                )
            }

            StringPreference::class -> function.returnType.isFlowOf(String::class).ifNot {
                logger.logMismatchedReturnTypeError(
                    functionName = functionName,
                    accessorAnnotation = GetFlow::class.simpleName ?: return@ifNot,
                    valueTypeAnnotation = valueTypeAnnotation::class.simpleName ?: return@ifNot,
                    expectedReturnType = "Flow<String>",
                )
            }

            else -> {
                logger.logMissingAnnotationError(functionName, ValueTypeAnnotations.allString)
                false
            }
        }
    }
}
