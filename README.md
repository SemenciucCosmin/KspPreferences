<h1 align="center">KSP Preferences</h1>

<p align="center">
  <strong>Type-safe DataStore — generated from a Kotlin interface, zero boilerplate.</strong>
</p>

<p align="center">
  <a href="https://central.sonatype.com/artifact/io.github.semenciuccosmin/preferences-annotations"><img alt="Maven Central" src="https://img.shields.io/maven-central/v/io.github.semenciuccosmin/preferences-annotations?label=Maven%20Central&color=4CAF50"/></a>
  <a href="https://www.apache.org/licenses/LICENSE-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <img alt="Kotlin" src="https://img.shields.io/badge/Kotlin-2.x-7F52FF?logo=kotlin&logoColor=white"/>
  <img alt="KSP" src="https://img.shields.io/badge/KSP-2.x-orange"/>
  <img alt="Platform" src="https://img.shields.io/badge/Platform-Android%2026%2B-3DDC84?logo=android&logoColor=white"/>
  <img alt="API" src="https://img.shields.io/badge/minSdk-26-3DDC84"/>
</p>

---

## ✨ Overview

**KSP Preferences** eliminates the DataStore boilerplate in your Android project.  
Annotate a plain Kotlin interface, and the KSP compiler plugin generates a fully-featured, type-safe DataStore implementation at build time — no reflection, no runtime overhead.

```
Your Interface  ──▶  @Preferences + type annotations  ──▶  KSP generates Impl
```

---

## 📦 Installation

Add the two artifacts to your module's `build.gradle.kts`:

```kotlin
plugins {
    id("com.google.devtools.ksp") version "2.3.6"   // match your Kotlin version
}

dependencies {
    // Annotations (compile-time only)
    implementation("io.github.semenciuccosmin:preferences-annotations:1.0.0")

    // KSP processor (code generator)
    ksp("io.github.semenciuccosmin:preferences-compiler:1.0.0")

    // Jetpack DataStore (required at runtime)
    implementation("androidx.datastore:datastore-preferences:1.2.1")
}
```

> **Note** Both artifacts are published on **Maven Central** — no extra repository configuration needed.

---

## 🚀 Quick Start

### 1 — Define your preferences interface

```kotlin
import io.github.semenciuccosmin.preferences.annotations.*
import kotlinx.coroutines.flow.Flow

@Preferences(name = "user_preferences")
interface UserPreferences {

    // One-shot suspending read
    @Get
    @StringPreference(key = "username", defaultValue = "")
    suspend fun getUsername(): String

    // Reactive Flow read
    @GetFlow
    @StringPreference(key = "username", defaultValue = "")
    fun getUsernameFlow(): Flow<String>

    // Suspending write
    @Set
    @StringPreference(key = "username", defaultValue = "")
    suspend fun setUsername(value: String)

    // Clear everything
    @Clear
    suspend fun clear()
}
```

### 2 — Read & write

```kotlin
// suspend context
val name = prefs.getUsername()
prefs.setUsername("Cosmin")
prefs.clear()

// Compose / ViewModel
val name by prefs.getUsernameFlow().collectAsState(initial = "")
```

---

## 🏭 PreferencesFactory

`PreferencesFactory` is a lightweight helper that instantiates the generated `*Impl` class for you using reflection — no DI framework required.

```kotlin
import io.github.semenciuccosmin.preferences.factory.PreferencesFactory

val prefs: UserPreferences = PreferencesFactory.create(context)
```

The type parameter `T` is reified, so the factory resolves the correct `UserPreferencesImpl` automatically at runtime.

> **Note** If you are already using a DI framework (e.g. Hilt, Koin) prefer binding the generated `*Impl` directly as a singleton inside your DI module. `PreferencesFactory` is intended for cases where a full DI setup is not available or desired.

---

## 🗂 Annotation Reference

### Class-level

| Annotation | Description |
|---|---|
| `@Preferences(name)` | Marks an interface as a DataStore container. `name` becomes the DataStore file name. |

### Function-level — operations

| Annotation | Function type | Description |
|---|---|---|
| `@Get` | `suspend fun foo(): T` | One-shot read; returns the stored value or the default. |
| `@GetFlow` | `fun foo(): Flow<T>` | Reactive read; emits on every change. |
| `@Set` | `suspend fun foo(value: T)` | Persists the supplied value. |
| `@Clear` | `suspend fun foo()` | Removes **all** keys from the DataStore. |

### Function-level — value types

| Annotation | Kotlin type | Default value |
|---|---|---|
| `@StringPreference(key, defaultValue)` | `String` | `""` |
| `@IntPreference(key, defaultValue)` | `Int` | `0` |
| `@BooleanPreference(key, defaultValue)` | `Boolean` | `false` |
| `@LongPreference(key, defaultValue)` | `Long` | `0L` |
| `@FloatPreference(key, defaultValue)` | `Float` | `0f` |
| `@DoublePreference(key, defaultValue)` | `Double` | `0.0` |
| `@ObjectPreference(key, clazz)` | `T?` | `null` |

> **Note** The class passed to `@ObjectPreference(clazz = ...)` must be annotated with `@Serializable` (kotlinx.serialization), otherwise serialization will fail at runtime. Object preferences always return a nullable type — `null` is yielded when the key is absent.

---

## 📐 Full Interface Example

```kotlin
import kotlinx.serialization.Serializable

@Serializable
data class UserProfile(val id: String, val name: String)

@Preferences(name = "sample_preferences")
interface SamplePreferences {

    @Get    @BooleanPreference(key = "dark_mode",  defaultValue = false)
    suspend fun getDarkMode(): Boolean

    @GetFlow @BooleanPreference(key = "dark_mode", defaultValue = false)
    fun getDarkModeFlow(): Flow<Boolean>

    @Set    @BooleanPreference(key = "dark_mode",  defaultValue = false)
    suspend fun setDarkMode(value: Boolean)

    @Get    @IntPreference(key = "launch_count",  defaultValue = 0)
    suspend fun getLaunchCount(): Int

    @GetFlow @IntPreference(key = "launch_count", defaultValue = 0)
    fun getLaunchCountFlow(): Flow<Int>

    @Set    @IntPreference(key = "launch_count",  defaultValue = 0)
    suspend fun setLaunchCount(value: Int)

    @Get    @LongPreference(key = "last_sync_ms", defaultValue = 0L)
    suspend fun getLastSyncMs(): Long

    @Get    @FloatPreference(key = "font_scale",  defaultValue = 1f)
    suspend fun getFontScale(): Float

    @Get    @DoublePreference(key = "latitude",   defaultValue = 0.0)
    suspend fun getLatitude(): Double

    @Get    @StringPreference(key = "auth_token",  defaultValue = "")
    suspend fun getAuthToken(): String

    // clazz must be @Serializable — returns null when the key is absent
    @Get    @ObjectPreference(key = "profile", clazz = UserProfile::class)
    suspend fun getProfile(): UserProfile?

    @GetFlow @ObjectPreference(key = "profile", clazz = UserProfile::class)
    fun getProfileFlow(): Flow<UserProfile?>

    @Set    @ObjectPreference(key = "profile", clazz = UserProfile::class)
    suspend fun setProfile(value: UserProfile)

    @Clear
    suspend fun clear()
}
```

---

## 🏛 Maven Coordinates

| Artifact | Group | Version |
|---|---|---|
| `preferences-annotations` | `io.github.semenciuccosmin` | `1.0.0` |
| `preferences-compiler` | `io.github.semenciuccosmin` | `1.0.0` |

**Gradle (Kotlin DSL)**
```kotlin
implementation("io.github.semenciuccosmin:preferences-annotations:1.0.0")
ksp("io.github.semenciuccosmin:preferences-compiler:1.0.0")
```

**Gradle (Groovy DSL)**
```groovy
implementation 'io.github.semenciuccosmin:preferences-annotations:1.0.0'
ksp 'io.github.semenciuccosmin:preferences-compiler:1.0.0'
```

**Maven**
```xml
<dependency>
    <groupId>io.github.semenciuccosmin</groupId>
    <artifactId>preferences-annotations</artifactId>
    <version>1.0.0</version>
</dependency>
<!-- KSP processor — add via your KSP plugin config, not as a <dependency> -->
```

---

## 🔧 Requirements

| Component | Version |
|---|---|
| Android minSdk | 26 |
| Kotlin | 2.x |
| KSP | matching Kotlin version |
| Jetpack DataStore | 1.1+ |

---

## 📜 License

```
Copyright 2026 Semenciuc Cosmin

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
