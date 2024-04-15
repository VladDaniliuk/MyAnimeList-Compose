plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    id(libs.plugins.kotlin.serialization.get().pluginId)
}

kotlin.jvmToolchain(17)

android {
    namespace = "data.user"
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
    implementation(libs.bundles.ktor)
    implementation(libs.androidx.datastore.preferences)
    //data
    implementation(projects.data.common)
    implementation(projects.data.ktor)
    implementation(projects.data.multiaccounts)
}
