import java.util.Properties

plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.room)
}

kotlin.jvmToolchain(17)

android {
    namespace = "data.multiaccounts"
    compileSdk = 34

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        minSdk = 27

        resourceConfigurations += listOf("en")

        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())
        val apiKey = properties.getProperty("api.key")

        checkNotNull(apiKey) { "api.key not found in local.properties" }

        buildConfigField("String", "API_KEY", "\"$apiKey\"")
    }

    compileOptions {
        ksp.arg("room.generateKotlin", "true")
    }

    room.schemaDirectory("schemas")

    hilt.enableAggregatingTask = true

    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs += "-Xstring-concat=inline"
    }
}

dependencies {
    implementation(libs.google.dagger.hilt.android)
    ksp(libs.google.dagger.hilt.compiler)
    implementation(libs.androidx.security.crypto)
    implementation(projects.data.ktor)
    implementation(libs.bundles.ktor)
    implementation(projects.core.utils)
    implementation(libs.bundles.room)
    ksp(libs.androidx.room.compiler)
}
