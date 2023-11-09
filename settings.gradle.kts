pluginManagement {
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
        maven {
            url=uri("https://oss.sonatype.org/content/repositories/snapshots/") }

        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
        maven {
            url=uri("https://oss.sonatype.org/content/repositories/snapshots/") }

    }
}
rootProject.name = "SRManager"
include(":app")
include(":core:ui")
include(":core:common")
include(":core:designsystem")
include(":core:network")
include(":features:auth:auth_presentation")
include(":core:datastore")
include(":core:database")
include(":features:auth:auth_data")
include(":features:auth:auth_domain")
include(":features:report:report_presentation")
include(":features:report:report_data")
include(":features:report:report_domain")
include(":features:settings:settings_data")
include(":features:settings:settings_domain")
include(":features:settings:settings_presentation")
include(":core:data")
include(":core:domain")
