plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")

}

apply {
    from("$rootDir/base-module.gradle")
}

android {
    namespace = "com.srmanager.core.database"
}



dependencies {
    kapt (Room.roomCompiler)
    implementation(Room.roomCoroutine)
    implementation(Room.roomRuntime)
}
