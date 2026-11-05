import java.util.Properties
import kotlin.apply

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.gms.google-services")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("kotlin-kapt")
}

val localProperties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) {
        file.inputStream().use { load(it) }
    }
}

// Booleans get their own reader: an absent key is a legitimate "false" here,
// not a misconfiguration worth warning about.
fun flag(name: String, default: Boolean = false): Boolean =
    localProperties.getProperty(name)?.trim()?.toBooleanStrictOrNull() ?: default

// Retrofit rejects a blank base URL and one without a trailing slash, both with an
// exception at graph-construction time. Normalise here so a misconfigured key gives a
// build warning instead of a crash on launch.
fun baseUrl(name: String): String {
    val value = secret(name)
    if (value.isEmpty()) return "https://example.invalid/"
    return if (value.endsWith("/")) value else "$value/"
}

fun secret(name: String): String {
    val value = localProperties.getProperty(name)
    if (value.isNullOrBlank() || value == "REPLACE_ME") {
        logger.warn("WARNING: $name is not set in local.properties - building with an empty value")
        return ""
    }
    return value
}
android {
    namespace = "gourmet.cwaorecasting.hubapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "gourmet.cwaorecasting.hubapp"
        minSdk = 29
        targetSdk = 36
        versionCode = 7
        versionName = "7.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            // Лучше не держать ключи прямо в коде, используйте этот способ
            // или другой вам известный.

            // Ключ для вашего DEX-файла
            // Cм. local.properties
            buildConfigField(
                type = "String",
                name = "DexKey",
                value = "\"${secret("DEX_FILE_KEY")}\""
            )

            buildConfigField(
                type = "String",
                name = "DevKey",
                value = "\"${secret("DEV_KEY")}\""
            )

            buildConfigField(
                type = "String",
                name = "Agent",
                value = "\"${secret("AGENT")}\""
            )

            // TEST=true skips the emulator bail-out in PrepScreen so the WebView
            // flow can be exercised on an emulator. Never true for a store build.
            buildConfigField(
                type = "boolean",
                name = "Test",
                value = flag("TEST").toString()
            )

            // Backend host for the install/visit API. See local.properties.
            buildConfigField(
                type = "String",
                name = "ApiBaseUrl",
                value = "\"${baseUrl("BASE_URL")}\""
            )

            // PWA_TEMPLATE=true shows the PWA promo card (Land/Carousel) over the WebView.
            buildConfigField(
                type = "boolean",
                name = "PwaTemplate",
                value = flag("PWA_TEMPLATE").toString()
            )
        }

        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            // Ключ для вашего DEX-файла
            buildConfigField(
                type = "String",
                name = "DexKey",
                value = "\"${secret("DEX_FILE_KEY")}\""
            )

            buildConfigField(
                type = "String",
                name = "DevKey",
                value = "\"${secret("DEV_KEY")}\""
            )

            buildConfigField(
                type = "String",
                name = "Agent",
                value = "\"${secret("AGENT")}\""
            )

            // TEST=true skips the emulator bail-out in PrepScreen so the WebView
            // flow can be exercised on an emulator. Never true for a store build.
            buildConfigField(
                type = "boolean",
                name = "Test",
                value = flag("TEST").toString()
            )

            // Backend host for the install/visit API. See local.properties.
            buildConfigField(
                type = "String",
                name = "ApiBaseUrl",
                value = "\"${baseUrl("BASE_URL")}\""
            )

            // PWA_TEMPLATE=true shows the PWA promo card (Land/Carousel) over the WebView.
            buildConfigField(
                type = "boolean",
                name = "PwaTemplate",
                value = flag("PWA_TEMPLATE").toString()
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
        buildConfig = true
    }
    packaging {
        // 16 KB device page size compat — Google Play requires this for native libs (Nov 2025+)
        jniLibs {
            useLegacyPackaging = false
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.work:work-runtime-ktx:2.9.1")

    val composeBom = platform("androidx.compose:compose-bom:2024.09.00")
    implementation(composeBom)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.compose.ui:ui-text-google-fonts")
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
    debugImplementation("androidx.compose.ui:ui-tooling")

    implementation("androidx.navigation:navigation-compose:2.8.3")

    implementation("androidx.room:room-runtime:2.7.1")
    implementation("androidx.room:room-ktx:2.7.1")
    kapt("androidx.room:room-compiler:2.7.1")

    implementation("androidx.datastore:datastore-preferences:1.1.1")

    implementation("io.coil-kt:coil-compose:2.7.0")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    // ... другие ваши зависимости

    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    implementation("com.squareup.retrofit2:converter-gson:3.0.0")

    implementation("org.jsoup:jsoup:1.17.2")

    implementation("com.google.android.gms:play-services-ads-identifier:18.3.0")

    implementation("com.appsflyer:af-android-sdk:6.18.0")
    implementation("com.android.installreferrer:installreferrer:2.2")

    //region Firebase
    implementation(platform("com.google.firebase:firebase-bom:34.10.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-messaging")

    // при условии, что ваша DI билблиотека - это Koin
    implementation(platform("io.insert-koin:koin-bom:4.0.3"))
    implementation("io.insert-koin:koin-android")
    implementation("io.insert-koin:koin-androidx-compose")
    implementation("io.insert-koin:koin-compose-viewmodel")
    implementation("io.insert-koin:koin-androidx-workmanager")
    //endregion

    // ... другие ваши зависимости
}