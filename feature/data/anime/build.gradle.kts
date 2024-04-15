plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    alias(libs.plugins.ksp)
    id(libs.plugins.kotlin.serialization.get().pluginId)
    alias(libs.plugins.hilt)
}

kotlin.jvmToolchain(17)

android {
    namespace = "shov.studio.data.anime"
    compileSdk = 34

    defaultConfig {
        minSdk = 27

        resourceConfigurations += "en"
    }

    hilt.enableAggregatingTask = true

    compileOptions.sourceCompatibility = JavaVersion.VERSION_17
    compileOptions.targetCompatibility = JavaVersion.VERSION_17

    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs += "-Xstring-concat=inline"
    }
}

dependencies {
    implementation(libs.google.dagger.hilt.android)
    ksp(libs.google.dagger.hilt.compiler)
    //api
    implementation(libs.androidx.paging.common)
    implementation(libs.bundles.ktor)
    //data
    implementation(projects.data.multiaccounts)
    implementation(projects.data.ktor)
    //models
    implementation(projects.core.enums)
    implementation(projects.data.common)
}
