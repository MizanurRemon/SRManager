plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

apply {
    from("$rootDir/compose-module.gradle")
}

android {
    namespace = "com.srmanager.core.designsystem"
}

dependencies {
    api(Compose.JUNIT_KTX)
    implementation(project(Modules.common))
    implementation(project(Modules.ui))
    implementation(project(Modules.network))
    implementation(project(Modules.order_domain))
    implementation ("se.warting.signature:signature-pad:0.1.2") // jetpack Compose views
}