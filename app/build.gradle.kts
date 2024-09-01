import java.util.Properties
import java.io.FileInputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

android {
    namespace = ProjectConfig.appId
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        applicationId = ProjectConfig.appId
        minSdk = ProjectConfig.minSdk
        targetSdk = ProjectConfig.targetSdk
        versionCode = ProjectConfig.versionCode
        versionName = ProjectConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    bundle {
        language {
            enableSplit = false
        }
    }

    signingConfigs {
        create("release") {
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storeFile = file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true

            signingConfig = signingConfigs.getByName("release")

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            isDebuggable = true
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = ProjectConfig.jvmTarget
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Compose.composeCompilerVersion
    }
    packaging {
        resources {
            excludes += "/META-INF/*"
            excludes += "/META-INF/LICENSE.md"
            excludes += "/META-INF/LICENSE-notice.md"
        }
    }

    applicationVariants.all {
        val variant = this
        variant.outputs
            .map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }
            .forEach { output ->
                val formatter = DateTimeFormatter.ofPattern("ddMMyy_HHmm")
                val formattedDate = LocalDateTime.now().format(formatter)
                val outputFileName =
                    "SRM_${formattedDate}.apk"
                output.outputFileName = outputFileName
            }
    }


}

dependencies {
    coreLibraryDesugaring(Desugar.desugar_jdk_libs)
    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)
    implementation(Compose.viewModelCompose)
    implementation(Coil.coilCompose)

    implementation(Hilt.hiltAndroidVersion)
    kapt(Hilt.hiltCompiler)
    implementation(project(Modules.ui))
    implementation(Retrofit.retrofit)
    implementation(Retrofit.okHttp)
    implementation(Retrofit.okHttpLoggingInterceptor)

    implementation(Coroutines.coroutines)

    implementation(project(Modules.common))
    implementation(project(Modules.network))
    implementation(project(Modules.database))
    implementation(project(Modules.datastore))
    implementation(project(Modules.designsystem))
    implementation(project(Modules.domain))
    implementation(project(Modules.data))

    implementation(project(Modules.auth_presentation))
    implementation(project(Modules.auth_domain))
    implementation(project(Modules.auth_data))

    implementation(project(Modules.outlet_presentation))
    implementation(project(Modules.outlet_domain))
    implementation(project(Modules.outlet_data))

    implementation(project(Modules.report_presentation))

    implementation(project(Modules.order_data))
    implementation(project(Modules.order_domain))
    implementation(project(Modules.order_presentation))

    implementation(project(Modules.summary_presentation))

    //Compose
    implementation(Compose.compiler)
    implementation(Compose.ui)
    implementation(Compose.uiToolingPreview)
    implementation(Compose.hiltNavigationCompose)
    implementation(Compose.material3)
    implementation(Compose.runtime)
    implementation(Compose.navigation)
    implementation(Compose.activityCompose)
    implementation(Compose.constraintlayoutCompose)
    implementation(Compose.GSON)
    implementation(Compose.DATASTORE_PREFERENCES)
    implementation(Compose.RETROFIT2_KOTLIN_SERIALIZATION_CONVERTER)
    implementation(Compose.KOTLINX_SERIALIZATION_JSON)
    implementation(Compose.ACCOMPANIST_SYSTEMUICONTROLLER)
    implementation(Compose.ACCOMPANIST_PAGER)
    implementation(Compose.GLIDE)
    androidTestImplementation("junit:junit:4.13.2")
    implementation(Compose.GLIDE)
    implementation(Compose.play_service_location)
    implementation(Compose.AccompanistPermissions)

    debugImplementation(Compose.uiTool)

    //app update
    implementation(Compose.appUpdate)
    implementation(Compose.inAppUpdate)

    //firebase
    implementation(platform(Firebase.FIREBASE_BOM))
    implementation(Firebase.FIREBASE_CRASHLYTICS)
    implementation(Firebase.FIREBASE_ANALYTICS)

    implementation(Compose.Lottie)
}

