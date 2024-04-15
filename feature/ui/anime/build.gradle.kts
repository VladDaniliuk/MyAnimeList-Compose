plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

kotlin.jvmToolchain(17)

android {
    namespace = "shov.studio.ui.anime"
    compileSdk = 34

    defaultConfig {
        minSdk = 27

        resourceConfigurations += "en"
    }

    hilt.enableAggregatingTask = true

    buildFeatures.compose = true
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
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(projects.core.mvi)
    //ui
    implementation(libs.androidx.compose.material.icons.exteded)
    implementation(libs.androidx.compose.material.theme)
    implementation(libs.androidx.compose.material3)
    implementation(projects.feature.ui.user)
    //coil
    implementation(libs.coil.network)
    implementation(libs.coil.compose)
    //navigator
    implementation(projects.core.navigator)
    implementation(projects.feature.settings.navigator)
    implementation(projects.feature.navigator)
    //domain
    implementation(projects.feature.domain.anime)
    implementation(projects.feature.domain.manga)
    implementation(projects.feature.domain.core)
    implementation(projects.feature.domain.auth)
    //core
    implementation(projects.core.ui)
    implementation(projects.core.enums)
    implementation(projects.core.utils)
    //utils
    implementation(libs.jetbrains.kotlinx.collections.immutable)
    implementation(libs.androidx.paging.compose)
    //tests
    implementation(libs.androidx.compose.ui.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)
}
