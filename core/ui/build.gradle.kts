plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

apply {
    from("$rootDir/compose-module.gradle")
}

android {
    namespace = "com.srmanager.core.ui"
}

dependencies {
    implementation("androidx.test.ext:junit-ktx:1.1.5")
    androidTestImplementation("org.testng:testng:6.9.6")
    androidTestImplementation("junit:junit:4.12")
}