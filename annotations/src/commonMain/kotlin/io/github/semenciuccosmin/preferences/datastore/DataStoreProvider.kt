package io.github.semenciuccosmin.preferences.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path

/**
 * Platform-agnostic factory for obtaining a singleton [DataStore] instance.
 *
 * Generated implementation classes call this once in their initialiser block.
 * Each unique [name] maps to exactly one [DataStore] — repeated calls with the
 * same [name] return the cached instance.
 *
 * @param context A platform-specific context object.
 *   - **Android**: `android.content.Context`
 *   - **JVM (Desktop)**: a `String` path to the parent directory for DataStore files.
 *   - **iOS**: `Unit` (unused; the file is resolved via `NSDocumentDirectory`).
 * @param name The logical name of the preferences DataStore file.
 */
fun createDataStore(context: Any, name: String): DataStore<Preferences> {
    return PreferenceDataStoreFactory.createWithPath(
        produceFile = { dataStorePreferencesPath(context, name) }
    )
}

/**
 * Resolves the platform-specific file path for a DataStore preferences file.
 */
expect fun dataStorePreferencesPath(context: Any, name: String): Path
