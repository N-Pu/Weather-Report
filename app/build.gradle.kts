
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
}

android {
    namespace = "com.project.weatherreport"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.project.weatherreport"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")


    val retrofit_version = "2.9.0"
    implementation ("com.squareup.retrofit2:retrofit:$retrofit_version")
//    retrofit Gson
    val retrofit_gson_version = "2.9.0"
    implementation ("com.squareup.retrofit2:converter-gson:$retrofit_gson_version")

//    def retrofit_moshi_version = '2.9.0'
//    def kotlin_moshi_version = '1.15.0'
//    implementation "com.squareup.retrofit2:converter-moshi:" + retrofit_moshi_version
//    implementation "com.squareup.moshi:moshi-kotlin:" + kotlin_moshi_version

    val http_version = "4.10.0"
    implementation ("com.squareup.okhttp3:logging-interceptor:$http_version")

    val cor_version = "1.7.1"
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:$cor_version")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:$cor_version")

    //ViewModel for Compose
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")

    // compose paging 3.0
//    def paging_version = "3.2.0"
//
//    implementation "androidx.paging:paging-runtime-ktx:$paging_version"
//    implementation "androidx.paging:paging-compose:$paging_version"


    //navigation compose
    implementation ("androidx.navigation:navigation-compose:2.6.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-compose:2.6.1")

    //COIL (image loader)
    implementation ("io.coil-kt:coil-compose:2.4.0")

    //ExoPlayer
//    implementation 'androidx.media3:media3-exoplayer:1.0.0'
//    implementation 'androidx.media3:media3-exoplayer-dash:1.0.0'
//    implementation 'androidx.media3:media3-ui:1.0.0'                  // Add this later


    // Splash Api
    implementation ("androidx.core:core-splashscreen:1.0.1")



    kapt ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    kapt ("androidx.lifecycle:lifecycle-common:2.6.1")
    kapt ("androidx.room:room-compiler:2.5.2")


    // dagger-hilt
//    implementation 'com.google.dagger:hilt-android:2.47'
//    kapt 'com.google.dagger:hilt-android-compiler:2.47'
//    implementation "androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03"
//    kapt "androidx.hilt:hilt-compiler:1.0.0"
//    implementation 'androidx.hilt:hilt-navigation-compose:1.0.0'


    //status bar colors
    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.30.1")

    // Splash Api
    implementation ("androidx.core:core-splashscreen:1.0.1")
}