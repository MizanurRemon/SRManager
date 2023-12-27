plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

apply {
    from("$rootDir/compose-module.gradle")
}

android {
    namespace = "com.srmanager.auth_presentation"
}

dependencies {
    implementation(project(Modules.designsystem))
    implementation(project(Modules.common))
    implementation(project(Modules.ui))
    implementation(project(Modules.network))

    implementation(project(Modules.database))
    implementation(project(Modules.datastore))
    implementation(Compose.DATASTORE_PREFERENCES)

    implementation(project(Modules.auth_domain))
}