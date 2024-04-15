plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
}

kotlin.jvmToolchain(17)

android {
    namespace = "shov.studio.feature.domain.models"
    compileSdk = 34

    defaultConfig {
        minSdk = 27

        resourceConfigurations += "en"
    }

    compileOptions.sourceCompatibility = JavaVersion.VERSION_17
    compileOptions.targetCompatibility = JavaVersion.VERSION_17

    kotlinOptions.jvmTarget = "17"
}

dependencies {
    implementation(projects.data.user)
    implementation(projects.data.multiaccounts)
}
