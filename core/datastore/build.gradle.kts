plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

apply {
    from("$rootDir/base-module.gradle")
}

android {
    namespace = "com.srmanager.core.datastore"
}

dependencies {
    implementation(Compose.DATASTORE_PREFERENCES)
}
