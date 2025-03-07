plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(project(":core"))
}