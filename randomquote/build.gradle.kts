plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "com.example.randomquote"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.com.example.randomquote"
        minSdk = 26
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Retrofit core
    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    // Converter library (Gson)
    implementation("com.squareup.retrofit2:converter-gson:3.0.0")

    // For viewModelScope (Coroutine Scope)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:+")  //2.10.0

    // Room dependencies
    val room_version = "2.8.4"
    implementation("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")

    // Work Manager dependency
    implementation("androidx.work:work-runtime-ktx:2.11.0")
}