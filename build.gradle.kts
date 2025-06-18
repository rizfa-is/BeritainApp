// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    project.extra.set("appId", "com.issog.beritainapp")
    project.extra.set("minSdk", 24)
    project.extra.set("targetSdk", 35)
    project.extra.set("compileSdk", 35)
    project.extra.set("versionCode", 1)
    project.extra.set("versionName", "1.0.0")
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.ksp) apply false
    alias(libs.plugins.android.dynamic.feature) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.ktlint) apply false
}

