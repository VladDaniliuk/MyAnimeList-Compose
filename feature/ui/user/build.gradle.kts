plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

kotlin.jvmToolchain(17)

android {
    namespace = "shov.studio.ui.user"
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
    implementation(projects.core.navigator)
    implementation(projects.feature.navigator)
    implementation(projects.core.mvi)
    //domain
    implementation(projects.feature.domain.user)
    implementation(projects.feature.domain.auth)
    //ui
    implementation(projects.core.ui)
    implementation(libs.androidx.browser)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.theme)
    implementation(libs.androidx.paging.compose)
    implementation(projects.feature.ui.models)
    //coil
    implementation(libs.coil.network)
    implementation(libs.coil.compose)
    //utils
    implementation(libs.jetbrains.kotlinx.collections.immutable)
    implementation(projects.core.utils)
    //tests
    implementation(libs.androidx.compose.ui.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)
}
