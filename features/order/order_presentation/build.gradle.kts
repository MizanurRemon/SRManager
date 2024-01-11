plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}


apply {
    from("$rootDir/compose-module.gradle")
}

android {
    namespace = "com.srmanager.order_presentation"
}


dependencies {
    implementation(project(Modules.designsystem))
    implementation(project(Modules.common))
    implementation(project(Modules.ui))
    implementation(Compose.material3)
    implementation(project(Modules.order_domain))
}