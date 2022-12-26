apply("${rootProject.projectDir}/config.gradle.kts")
plugins {
    id("com.android.application") version "7.3.1"
    id("org.jetbrains.kotlin.android") version "1.7.20"
    id("com.google.gms.google-services")
    id("kotlin-parcelize")
    id("de.mannodermaus.android-junit5")
    kotlin("kapt")
}
android {
    namespace = "com.groupe5.steamfav"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.groupe5.steamfav"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true

    }
    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField(
                "String",
                "STEAM_STORE_API_BASE_URL",
                "\"http://localhost:3001/api/\""
            )
            buildConfigField("String", "STEAM_WORKS_WEB_API_BASE_URL", "\"http://localhost:3001\"")
            buildConfigField("String", "STEAM_STORE_BASE_URL", "\"http://localhost:3001\"")
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField(
                "String",
                "STEAM_STORE_API_BASE_URL",
                "\"https://store.steampowered.com/api\""
            )
            buildConfigField(
                "String",
                "STEAM_WORKS_WEB_API_BASE_URL",
                "\"https://api.steampowered.com\""
            )
            buildConfigField(
                "String",
                "STEAM_STORE_BASE_URL",
                "\"https://store.steampowered.com/\""
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"

    }
    buildFeatures {
        viewBinding = true
    }


}

val retrofit_version by extra("2.9.0")
dependencies {
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("com.google.android.material:material:1.7.0")
    // Google
    implementation("com.google.android.gms:play-services-auth:20.4.0")


    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:31.1.0"))
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-storage-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")

    // Firebase UI
    implementation("com.firebaseui:firebase-ui-auth:8.0.2")
    implementation("com.firebaseui:firebase-ui-database:8.0.2")
    //http and api dependencies
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.squareup.moshi:moshi:1.14.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.14.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.14.0")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")
    // end of http and api dependencies
    implementation("com.github.bumptech.glide:glide:4.14.2")
    kapt("com.github.bumptech.glide:compiler:4.14.2")
    //assertj
    testImplementation("org.assertj:assertj-core:3.23.1")
    //mock web server
    testImplementation("com.squareup.okhttp3:mockwebserver:4.10.0")
    //coroutine test
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    //junit5
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.1")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.1")
    //Mockito
    testImplementation("org.mockito:mockito-core:4.8.1")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
    testImplementation("org.mockito:mockito-inline:3.12.4")
    testImplementation("org.mockito:mockito-junit-jupiter:4.8.1")
    //espresso ui test
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")
}