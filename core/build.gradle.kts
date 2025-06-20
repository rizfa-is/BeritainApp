plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.ksp)
}

android {
    namespace = "com.issog.core"
    compileSdk = rootProject.extra.get("compileSdk") as Int?

    defaultConfig {
        minSdk = rootProject.extra.get("minSdk") as Int?

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        externalNativeBuild {
            cmake {
                cppFlags("")
            }
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isMinifyEnabled = true
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
    externalNativeBuild {
        cmake {
            path(file("src/main/cpp/CMakeLists.txt"))
            version = "3.22.1"
        }
    }
}

dependencies {

    api(libs.androidx.core.ktx)
    api(libs.androidx.appcompat)
    api(libs.material)
    api(libs.androidx.activity)
    api(libs.androidx.constraintlayout)
    api(libs.glide)

    // fragment
    api(libs.androidx.navigation.ui)
    api(libs.androidx.navigation.fragment)

    // room
    api(libs.androidx.room)
    ksp(libs.androidx.roomcompiler)
    api(libs.sql.cipher)

    // retrofit
    api(libs.retrofit2)
    api(libs.retrofit2.gsonconverter)
    api(libs.okhttp3.logging)

    // koin
    api(libs.koin.core)
    api(libs.koin.android)

    //lifecycle
    api(libs.lifecycle.viewmodel.ktx)
    api(libs.lifecycle.livedata.ktx)

    // paging
    api(libs.androidx.paging.runtime.ktx)

    // chucker
    debugApi(libs.chucker.debug)
    releaseApi(libs.chucker.release)

    // dynamic feature
    api(libs.feature.delivery)

    // leakcanary
    debugApi(libs.leakcanary)

    testImplementation(libs.junit.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //kotlin flow test
    testImplementation(libs.turbine)
    testImplementation(libs.truth)
    testImplementation(libs.coroutine.test)
}