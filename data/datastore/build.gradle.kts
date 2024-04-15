plugins {
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    defaultConfig {
        resourceConfigurations += "en"
    }

    hilt.enableAggregatingTask = true
}

dependencies {
    ksp(libs.google.dagger.hilt.compiler)
}
