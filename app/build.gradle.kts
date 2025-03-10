plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.0"
}

android {
    namespace = "com.example.myapplicationtv"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.myapplicationtv"
        minSdk = 23
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

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
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.tv.foundation)
    implementation(libs.androidx.tv.material)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.material3.android)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Hilt
    // Hilt dependency injection library
    implementation(libs.hilt.android)
    // Hilt compiler for annotation processing
    ksp(libs.hilt.android.compiler)
    // for hiltViewModel()
    implementation(libs.androidx.hilt.navigation.compose)

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

    // Coroutines: A Kotlin library that provides support for asynchronous programming and concurrency.
    // Core Coroutines: Provides the core functionality for coroutines.
    implementation(libs.coroutines.core)
    // Android Coroutines: Provides Android-specific coroutine support.
    implementation(libs.coroutines.android)

    // ExoPlayer core library for media playback
    implementation(libs.exoplayer.core)
    // ExoPlayer UI components for media controls
    implementation(libs.exoplayer.ui)

    // media
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.ui)
    implementation(libs.androidx.media3.exoplayer.hls)

    // Coil
    implementation(libs.coil)
    implementation(libs.coil.compose)

    // Navigation
    implementation(libs.androidx.navigation.compose)
}