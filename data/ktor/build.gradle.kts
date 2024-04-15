import java.util.Properties

plugins {
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        resourceConfigurations += listOf("en")

        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())
        val apiKey = properties.getProperty("api.key")

        checkNotNull(apiKey) { "api.key not found in local.properties" }

        buildConfigField("String", "API_KEY", "\"$apiKey\"")
    }

    hilt.enableAggregatingTask = true
}

dependencies {
    ksp(libs.google.dagger.hilt.compiler)
}
