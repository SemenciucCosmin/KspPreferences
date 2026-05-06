# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

---

## [2.0.0] — 2026-05-06

### Added
- **Kotlin Multiplatform (KMP) support** — the library now targets Android, iOS (arm64 + simulatorArm64), and JVM Desktop.
- `@ConstructedBy(constructor)` — annotation that links a `@Preferences` interface to a `PreferencesConstructor` object, enabling reflection-free instantiation on all platforms (modelled after Room 3's `@ConstructedBy`).
- `PreferencesConstructor<T>` — interface implemented by a KSP-generated `actual object` that provides a type-safe `initialize(context)` factory method.
- `PreferencesFactory.create(constructor, context)` — constructor-based factory for KMP projects; works on Android, iOS, and Desktop without reflection.
- `PreferencesFactory.create<T>(context)` — reflection-based factory for non-KMP Android/JVM projects; resolves the generated `*Impl` class via `Class.forName`.
- `GenerateConstructorObjectUseCase` — new KSP processor use-case that generates the `actual object` (or plain `object` for non-KMP) implementing `PreferencesConstructor`.
- `sampleAndroid` module — a traditional single-platform Android sample app demonstrating the reflection-based API without KMP.

### Changed
- `PreferencesFactory` is now an empty `object`; both `create` overloads are top-level extension functions for correct overload resolution between the constructor-based and reflection-based variants.
- `@ConstructedBy` is optional — KMP projects use it for type-safe, reflection-free instantiation; non-KMP projects can omit it and use `PreferencesFactory.create<T>(context)` instead.
- The KSP processor detects `expect` vs plain declarations via `Modifier.EXPECT` and conditionally generates `actual object` (KMP) or `object` (non-KMP).
- JVM Desktop DataStore path changed from a relative `datastore/` directory to an absolute path under `~/.local/share/datastore/` for stability across different working directories.

### Removed
- The previous platform-specific `PreferencesFactory` implementations using `expect object PreferencesFactory` with `actual` members have been replaced by the new extension-function-based approach.

---

## [1.1.0]

### Added
- `@ObjectPreference(key, clazz)` — serializable object preference support via `kotlinx.serialization`; returns a nullable type (`T?`).
- `PreferencesFactory.create<T>(context)` — reflection-based factory helper for instantiating generated `*Impl` classes without a DI framework.

---

## [1.0.0] — 2026-04-03

### Added

#### Annotations (`preferences-annotations`)
- `@Preferences(name)` — marks an interface as a KSP-managed DataStore container.
- `@Get` — generates a suspending one-shot read function.
- `@GetFlow` — generates a `Flow`-returning reactive read function.
- `@Set` — generates a suspending write function.
- `@Clear` — generates a suspending function that removes all DataStore entries.
- `@BooleanPreference(key, defaultValue)` — Boolean preference support.
- `@DoublePreference(key, defaultValue)` — Double preference support.
- `@FloatPreference(key, defaultValue)` — Float preference support.
- `@IntPreference(key, defaultValue)` — Int preference support.
- `@LongPreference(key, defaultValue)` — Long preference support.
- `@StringPreference(key, defaultValue)` — String preference support.

#### Compiler (`preferences-compiler`)
- KSP symbol processor that validates annotated interfaces at compile time and generates a fully type-safe `*Impl` DataStore implementation.
- Validation covers annotation composition rules, return types, parameter counts, and `suspend` modifier requirements; all errors are reported in a single build pass without short-circuiting.
- Generated implementation uses a file-scoped `preferencesDataStore` delegate to ensure a single DataStore instance per file, preventing the _"multiple DataStores active for the same file"_ runtime error.
- Koin-based internal DI wiring for all processor use-cases and code generators.
- Detekt static analysis integration with auto-correct and a custom rule configuration.

#### Sample
- Sample application demonstrating all supported preference types with `@Get`, `@GetFlow`, `@Set`, and `@Clear` operations.
- Instrumented test suite (`SamplePreferencesTest`) covering every value type and operation, using Turbine for Flow assertions.
- Koin singleton binding for `SamplePreferences` in tests to avoid multiple DataStore instances on the same file.
