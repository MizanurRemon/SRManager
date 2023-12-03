plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.srmanager.outlet_presentation"

}

apply {
    from("$rootDir/compose-module.gradle")
}

dependencies {
    implementation(project(Modules.designsystem))
    implementation(project(Modules.common))
    implementation(project(Modules.ui))
    implementation(project(Modules.network))
    implementation(Compose.material3)
    implementation(project(Modules.database))
    implementation(Coil.coilCompose)

    implementation(project(Modules.outlet_domain))
}