allprojects {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

plugins {
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.room) apply false
}
