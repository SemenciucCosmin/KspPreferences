package io.github.semenciuccosmin.preferences.factory

actual object PreferencesFactory {
    actual inline fun <reified T : Any> create(context: Any?): T {
        val implClass = Class.forName("${T::class.qualifiedName}Impl")
        @Suppress("UNCHECKED_CAST")
        return implClass.getDeclaredConstructor().newInstance() as T
    }
}

