plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    namespace = "com.htcmobilecoding"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.htcmobilecoding"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        /* ndk {
             abiFilters.add("armeabi-v7a")
             abiFilters.add("arm64-v8a")
             abiFilters.add("x86")
             abiFilters.add("x86_64")
         }*/
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

    /*  externalNativeBuild {
          cmake {
              path = file("src/main/cpp/CMakeLists.txt")
              version = "3.22.1"
          }
      }*/
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
    implementation(libs.play.services.location)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    implementation("com.google.dagger:hilt-android:2.56.1")
    kapt("com.google.dagger:hilt-compiler:2.56.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    implementation("androidx.navigation:navigation-compose:2.9.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.9.1")

    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    implementation("io.coil-kt:coil-compose:2.5.0")


    implementation (libs.compose)


}
kapt {
    correctErrorTypes = true
}