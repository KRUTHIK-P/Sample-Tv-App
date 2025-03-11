plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.0"
}

android {
    namespace = "com.example.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Hilt
    // Hilt dependency injection library
    implementation(libs.hilt.android)
    // Hilt compiler for annotation processing
    ksp(libs.hilt.android.compiler)

    // Room Database
    // Room runtime library for database operations
    implementation(libs.room.runtime)
    // Room Kotlin extensions for database operations
    implementation(libs.room.ktx)
    // Room compiler for annotation processing
    ksp(libs.room.compiler)

    /*** Ktor ***/
    // Ktor core client library for making HTTP requests
    implementation(libs.ktor.client.core)
    // Ktor client for Android platform to support Android-specific features
    implementation(libs.ktor.client.android)
    // Ktor client JSON support for handling JSON data
    implementation(libs.ktor.client.json)
    // Ktor client logging support for logging HTTP requests and responses
    implementation(libs.ktor.client.logging)
    // Ktor client serialization support for serializing and deserializing data
    implementation(libs.ktor.client.serialization)
    // Kotlinx serialization JSON library for JSON serialization and deserialization
    implementation(libs.kotlinx.serialization.json)
    // Ktor serialization with Kotlinx JSON for integrating Ktor with Kotlinx serialization
    implementation(libs.ktor.serialization.kotlinx.json)
    // Ktor client content negotiation support for handling different content types
    implementation(libs.ktor.client.content.negotiation)
    /*** Ktor ***/

    // DataStore: A Jetpack library that provides a robust and scalable solution for storing and managing key-value pairs or typed objects.
    // Preferences DataStore: Stores and accesses key-value pairs.
    implementation(libs.datastore.preferences)
    // Core DataStore: Provides the core functionality for DataStore.
    implementation(libs.datastore.core)

    implementation(project(":core"))
}