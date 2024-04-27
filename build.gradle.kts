buildscript {
    dependencies {
        //noinspection GradleDependency
        classpath("com.google.gms:google-services:4.3.15")
        //noinspection GradleDependency
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.9")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id (Build.androidGradlePlugin) version Build.androidGradlePluginVersion apply false
    id (Build.libraryGradlePlugin) version Build.androidGradlePluginVersion apply false
    id (Build.kotlinGradlePlugin) version Kotlin.version apply false
    id (Hilt.hiltAndroid) version Hilt.version apply false
}