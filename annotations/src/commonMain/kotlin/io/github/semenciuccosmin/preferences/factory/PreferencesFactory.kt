package io.github.semenciuccosmin.preferences.factory

expect object PreferencesFactory {
    inline fun <reified T : Any> create(context: Any? = null): T
}
