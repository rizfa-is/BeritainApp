import io.gitlab.arturbosch.detekt.Detekt

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint)
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
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            signingConfig = signingConfigs.getByName("debug")
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

    detekt {
        buildUponDefaultConfig = true // preconfigure defaults
        allRules = false // activate all available (even unstable) rules.
        config.setFrom(
            "$projectDir/config/detekt/detekt.yml",
        ) // point to your custom config defining rules to run, overwriting default behavior
        baseline = file("$projectDir/config/baseline.xml") // a way of suppressing issues before introducing detekt
    }
    tasks.withType<Detekt>().configureEach {
        reports {
            txt.required.set(true) // or false to disable the report
            html.required.set(true) // or false to disable the report
            xml.required.set(true) // or false to disable the report
        }
    }
    ktlint {
        debug.set(true)
        verbose.set(true)
        android.set(false)
        outputToConsole.set(true)
        outputColorName.set("RED")
        filter {
            enableExperimentalRules.set(true)
            exclude { projectDir.toURI().relativize(it.file.toURI()).path.contains("/generated/") }
            include("**/kotlin/**")
        }
    }
}

dependencies {
    api(project(":core"))

    testImplementation(libs.junit.junit)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // mock looper in viewmodel
    testImplementation(libs.test.core)

    // mockito
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.inline)

    // kotlin coroutines test
    testImplementation(libs.coroutine.test)

    // kotlin flow test
    testImplementation(libs.turbine)
    testImplementation(libs.truth)
}
