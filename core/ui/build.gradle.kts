plugins {
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    defaultConfig.resourceConfigurations += "en"

    hilt.enableAggregatingTask = true

    buildFeatures.compose = true
    composeOptions.kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
}

dependencies {
    kspAndroid(libs.google.dagger.hilt.compiler)
}
