plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

apply {
    from("$rootDir/base-module.gradle")
}

android {
    namespace = "com.srmanager.outlet_domain"
}

dependencies {
    implementation(project(Modules.network))
    implementation(project(Modules.common))
    implementation(project(Modules.database))
}