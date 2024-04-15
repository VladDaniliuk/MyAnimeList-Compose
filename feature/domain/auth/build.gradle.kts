plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

kotlin.jvmToolchain(17)

android {
    namespace = "shov.studio.domain.auth"
    compileSdk = 34

    defaultConfig {
        minSdk = 27

        resourceConfigurations += "en"
    }

    hilt.enableAggregatingTask = true

    compileOptions.sourceCompatibility = JavaVersion.VERSION_17
    compileOptions.targetCompatibility = JavaVersion.VERSION_17

    kotlinOptions.jvmTarget = "17"
}

dependencies {
    implementation(libs.google.dagger.hilt.android)
    ksp(libs.google.dagger.hilt.compiler)
    //utils
    implementation(projects.core.utils)
    implementation(libs.androidx.paging.runtime)
    //data
    implementation(libs.androidx.work.runtime.ktx)
    implementation(projects.data.multiaccounts)
    implementation(projects.data.user)
    implementation(projects.data.ktor)
    //domain
    implementation(projects.feature.domain.models)
    //worker
    implementation(libs.androidx.hilt.work)
    ksp(libs.androidx.hilt.compiler)
}