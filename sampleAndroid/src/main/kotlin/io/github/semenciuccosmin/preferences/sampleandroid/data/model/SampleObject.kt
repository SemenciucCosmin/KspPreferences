package io.github.semenciuccosmin.preferences.sampleandroid.data.model

import kotlinx.serialization.Serializable

/**
 * A sample serializable object used to demonstrate [io.github.semenciuccosmin.preferences.annotations.ObjectPreference]
 * support in the KSP Preferences library.
 *
 * Instances are serialized to JSON and stored as a [String] preference in the DataStore.
 *
 * @property id   A unique identifier for the object.
 * @property name A human-readable display name.
 */
@Serializable
data class SampleObject(
    val id: String,
    val name: String,
)
