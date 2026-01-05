import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")

    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.devhub.moviesapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.devhub.moviesapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        defaultConfig {

            buildConfigField(
                "String",
                "API_KEY",
                "\"${getApiKey()}\""
            )
            buildConfigField(
                "String",
                "BASE_URL",
                "\"${getBASEURL()}\""
            )
            buildConfigField(
                "String",
                "BASE_IMG_URL",
                "\"${getIMAGURL()}\""
            )
        }
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        buildConfig = true
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
fun Project.getApiKey(): String {
    return gradleLocalProperties(rootDir, providers)
        .getProperty("API_KEY") ?: ""
}

fun Project.getBASEURL(): String {
    return gradleLocalProperties(rootDir, providers)
        .getProperty("BASE_URL") ?: ""
}

fun Project.getIMAGURL(): String {
    return gradleLocalProperties(rootDir, providers)
        .getProperty("BASE_IMG_URL") ?: ""
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
    implementation(libs.core.ktx)
    implementation(libs.play.services.cast)
    implementation(libs.ui.graphics)
    testImplementation(libs.junit)
    testImplementation(libs.androidx.room.runtime.android)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Navigation
    implementation(libs.navigation.compose)

    // Room
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)
    implementation(libs.room.paging)
    testImplementation(libs.room.testing)
    androidTestImplementation(libs.room.testing)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    kapt(libs.androidx.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)

    // Lifecycle / ViewModel
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.lifecycle.viewmodel.compose)

    // Coil
    implementation(libs.coil.compose)

    // Paging
    implementation(libs.paging.runtime)
    implementation(libs.paging.compose)

    // Icons
    implementation(libs.material.icons.extended)

    // System UI
    implementation(libs.accompanist.systemuicontroller)

    // Testing
    testImplementation(libs.turbine)
    androidTestImplementation(libs.turbine)


}