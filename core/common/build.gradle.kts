plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

apply {
    from("$rootDir/base-module.gradle")
}

android {
    namespace = "com.srmanager.core.common"
}

dependencies {
    implementation(Coil.COIL)
    implementation(Compose.GUAVA)
    implementation(Compose.ui)
    implementation(platform(Firebase.FIREBASE_BOM))
    implementation(Firebase.FIREBASE_CRASHLYTICS)
    implementation(Firebase.FIREBASE_ANALYTICS)
}