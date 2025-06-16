plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = rootProject.extra.get("appId") as String?
    compileSdk = rootProject.extra.get("compileSdk") as Int?

    defaultConfig {
        applicationId = rootProject.extra.get("appId") as String?
        minSdk = rootProject.extra.get("minSdk") as Int?
        targetSdk = rootProject.extra.get("targetSdk") as Int?
        versionCode = rootProject.extra.get("versionCode") as Int?
        versionName = rootProject.extra.get("versionName") as String?

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
    dynamicFeatures += setOf(":favorite")
}

dependencies {
    api(project(":core"))

    testImplementation(libs.junit.junit)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //mock looper in viewmodel
    testImplementation(libs.test.core)

    //mockito
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.inline)

    // kotlin coroutines test
    testImplementation(libs.coroutine.test)

    //kotlin flow test
    testImplementation(libs.turbine)
    testImplementation(libs.truth)
}