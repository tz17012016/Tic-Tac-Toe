plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.tic_tac_toe"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.tic_tac_toe"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.gridlayout)
    annotationProcessor(libs.room.compiler)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.ext.junit)
    testImplementation(libs.junit)
    implementation(libs.recyclerview)
    implementation(libs.room.runtime)
    implementation(libs.room.common.jvm)
    implementation(libs.room.common)
    implementation(libs.navigation.ui)
    implementation(libs.navigation.fragment)
}