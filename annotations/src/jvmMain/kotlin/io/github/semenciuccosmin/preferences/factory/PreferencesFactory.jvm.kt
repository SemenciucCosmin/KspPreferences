package io.github.semenciuccosmin.preferences.factory

/**
 * JVM desktop implementation of the reflection-based preferences factory.
 *
 * Resolves the generated `*Impl` class via [Class.forName] and instantiates it
 * with the provided [context].
 */
actual inline fun <reified T : Any> PreferencesFactory.create(context: Any?): T {
    val implClass = Class.forName("${T::class.qualifiedName}Impl")
    @Suppress("UNCHECKED_CAST")
    return implClass.getDeclaredConstructor(Any::class.java).newInstance(context) as T
}

