import java.util.Properties

plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

kotlin.jvmToolchain(17)

android {
    namespace = "shov.studio.settings.ui"
    compileSdk = 34

    buildFeatures {
        compose = true
        buildConfig = true
    }

    defaultConfig {
        minSdk = 27

        resourceConfigurations += "en"

        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())
        val privacyLink = properties.getProperty("privacy.link")

        checkNotNull(privacyLink) { "privacy.link not found in local.properties" }

        buildConfigField("String", "PRIVACY_LINK", "\"$privacyLink\"")
    }

    hilt.enableAggregatingTask = true

    composeOptions.kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()

    compileOptions.sourceCompatibility = JavaVersion.VERSION_17
    compileOptions.targetCompatibility = JavaVersion.VERSION_17

    kotlinOptions.jvmTarget = "17"
}

dependencies {
    implementation(libs.google.dagger.hilt.android)
    ksp(libs.google.dagger.hilt.compiler)
    //architecture
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.browser)
    implementation(projects.core.mvi)
    //ui
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.exteded)
    implementation(projects.feature.settings.core)
    implementation(projects.core.ui)
    implementation(projects.feature.ui.user)
    implementation(projects.feature.ui.models)
    //coil
    implementation(libs.coil.network)
    implementation(libs.coil.compose)
    //navigator
    implementation(projects.core.navigator)
    implementation(projects.feature.settings.navigator)
    implementation(projects.feature.navigator)
    //domain
    implementation(projects.feature.settings.domain)
    implementation(projects.feature.domain.auth)
    implementation(projects.feature.domain.user)
    implementation(projects.feature.domain.models)
    //data
    implementation(libs.jetbrains.kotlinx.collections.immutable)
    //tests
    implementation(libs.androidx.compose.ui.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)
}

