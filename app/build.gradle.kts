plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.testsprint0projbio"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.testsprint0projbio"
        minSdk = 21 // Actualiza el minSdkVersion a 21 para soporte BLE
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        maxSdk = 34

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
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
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation(libs.androidx.work.runtime)
    implementation(libs.play.services.maps)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.play.services.location)

    // Retrofit dependencies for HTTP client
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.code.gson:gson:2.8.9")

    // Dependencies for QR scanning
    implementation("com.journeyapps:zxing-android-embedded:4.3.0")

    // OkHttp for HTTP logging
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")

    // Biometric authentication library
    implementation("androidx.biometric:biometric:1.1.0")

    // MPAndroidChart for circular charts
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")

    // Unit testing and Android test dependencies
    testImplementation("junit:junit:4.13.2")
    testImplementation(libs.androidx.junit)
    testImplementation(libs.androidx.espresso.core)
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    // Mocking and testing libraries
    testImplementation("org.mockito:mockito-core:3.12.4")
    testImplementation("androidx.work:work-testing:2.7.1")
    testImplementation("androidx.test:core:1.4.0")
    testImplementation("androidx.test.ext:junit-ktx:1.1.5")
    testImplementation("org.robolectric:robolectric:4.9")
}
