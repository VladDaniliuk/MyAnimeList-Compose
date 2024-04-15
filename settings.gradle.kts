pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()

        // Amper repositories
        maven("https://maven.pkg.jetbrains.space/public/p/amper/amper")
        maven("https://www.jetbrains.com/intellij-repository/releases")
        maven("https://packages.jetbrains.team/maven/p/ij/intellij-dependencies")
    }
}

plugins {
    id("de.fayard.refreshVersions") version "0.60.5"
    id("org.jetbrains.amper.settings.plugin") version "0.2.2"
}

refreshVersions {
    file("build/tmp/refreshVersions").mkdirs()
    versionsPropertiesFile = file("build/tmp/refreshVersions/versions.properties")
}

//For using implement(project.feature) instead of implement(project("feature"))
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

//TODO remove when Amper update Android Gradle Plugin version
buildscript {
    configurations.all {
        resolutionStrategy.eachDependency {
            when {
                requested.name == "javapoet" -> useVersion("1.13.0")
            }
        }
    }
}

rootProject.name = "MyAnimeList"

include(":app")
include(":feature:settings:core")
include(":feature:settings:data")
include(":feature:settings:domain")
include(":feature:settings:navigator")
include(":feature:settings:ui")
include(":feature:theme:ui")
include(":core:enums")
include(":feature:data:anime")
include(":data:common")
include(":data:datastore")
include(":data:multiaccounts")
include(":data:user")
include(":feature:domain:core")
include(":feature:domain:anime")
include(":feature:domain:auth")
include(":feature:domain:manga")
include(":feature:domain:user")
include(":feature:domain:models")
include(":feature:navigator")
include(":feature:ui:anime")
include(":feature:ui:user")
include(":feature:ui:models")
