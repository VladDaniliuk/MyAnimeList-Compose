import java.io.FileInputStream
import java.util.Properties

plugins {
    id(libs.plugins.android.application.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

kotlin {
    jvmToolchain(17)
}

android {
    namespace = "shov.studio.app"

    compileSdk = 34

    defaultConfig {
        minSdk = 27
        targetSdk = 34

        applicationId = "shov.studio.app"
        versionCode = 2
        versionName = "0.1.0"

        resourceConfigurations += "en"
    }

    signingConfigs {
        create("release") {
            val file = rootProject.file("local.properties")

            if (file.exists()) {
                val properties = Properties()
                properties.load(FileInputStream(file))

                storeFile = file(properties.getProperty("store.file"))
                storePassword = properties.getProperty("store.password")
                keyAlias = properties.getProperty("key.alias")
                keyPassword =properties.getProperty("key.password")
            }
        }
    }

    buildTypes {
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            signingConfig = signingConfigs.getByName("release")
        }

        debug {
            isDebuggable = true

            signingConfig = signingConfigs.getByName("debug")
        }
    }

    hilt.enableAggregatingTask = true

    buildFeatures.compose = true
    composeOptions.kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
}

dependencies {
    //android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    //architecture
    implementation(projects.core.mvi)
    //hilt
    implementation(libs.google.dagger.hilt.android)
    ksp(libs.google.dagger.hilt.compiler)
    //ui
    implementation(libs.google.accompanist.navigation.material)
    implementation(libs.androidx.core.splashscreen)
    //features
    implementation(projects.feature.ui.anime)
    implementation(projects.feature.settings.ui)
    implementation(projects.feature.theme.ui)
    implementation(projects.feature.ui.user)
    //navigator
    implementation(projects.feature.navigator)
    implementation(projects.core.navigator)
    //core
    implementation(projects.core.ui)
    //domain
    implementation(projects.feature.domain.auth)
    //worker
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.hilt.work)
}
