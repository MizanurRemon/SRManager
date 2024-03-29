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
include(":core:data")
include(":core:domain")
include(":features:outlet:outlet_presentation")
include(":features:report:report_presentation")
include(":features:outlet:outlet_data")
include(":features:outlet:outlet_domain")
include(":features:order:order_presentation")
include(":features:order:order_domain")
include(":features:order:order_data")
