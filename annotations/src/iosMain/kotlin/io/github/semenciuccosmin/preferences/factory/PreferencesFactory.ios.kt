package io.github.semenciuccosmin.preferences.factory

/**
 * iOS implementation of the reflection-based preferences factory.
 *
 * Reflection is not supported on iOS/Native. This overload always throws an error
 * directing the user to the [PreferencesConstructor]-based overload.
 */
actual inline fun <reified T : Any> PreferencesFactory.create(context: Any?): T {
    error(
        "Reflection-based PreferencesFactory.create<T>() is not supported on iOS. " +
            "Use @ConstructedBy with PreferencesFactory.create(constructor, context) instead."
    )
}

