plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
    id("com.google.gms.google-services")

}

android {
    namespace = "com.example.rememberme"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.rememberme"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.example.rememberme.CustomTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            isDebuggable = false
        }
        debug {
            isMinifyEnabled = false
            isDebuggable = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    lint {
        abortOnError = false
    }
}

dependencies {
    
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.lifecycle.runtime.compose.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    testImplementation(libs.androidx.core.testing)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    androidTestImplementation(libs.truth)
    androidTestImplementation(libs.core.testing)


    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    testImplementation(libs.androidx.room.room.testing)



    // Google font
    implementation(libs.androidx.ui.text.google.fonts)

    implementation(libs.androidx.junit.ktx)

    // ViewModel utilities for Compose
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Splash screen
    implementation(libs.androidx.core.splashscreen)

    // Navigation Compose
    implementation(libs.androidx.navigation.compose)
    androidTestImplementation(libs.androidx.navigation.testing)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    kapt(libs.hilt.compiler)

    // For Robolectric tests.
    testImplementation(libs.hilt.android.testing)
    // ...with Kotlin.
    kspTest(libs.hilt.android.compiler)

    // For instrumented tests.

    androidTestImplementation(libs.hilt.android.testing)
    // ...with Kotlin.
    kspAndroidTest(libs.hilt.android.compiler)
    // Hilt Worker
    implementation(libs.androidx.hilt.work)
    // When using Kotlin.
    kapt(libs.androidx.hilt.compiler)

    // Unit Test
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test)

    // Firebase Bom
    implementation(platform(libs.firebase.bom))

    // Robolectric
    testImplementation(libs.robolectric)

    // Lottie
    implementation(libs.lottie.compose)

    // compose animation
    implementation(libs.androidx.animation)

    // Data Store
    implementation(libs.androidx.datastore.preferences)

    // Work Manager
    // Kotlin + coroutines
    implementation(libs.androidx.work.runtime.ktx)
    // optional - Test helpers
    androidTestImplementation(libs.androidx.work.testing)
}