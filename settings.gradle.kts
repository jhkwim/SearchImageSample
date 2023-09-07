pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "SearchImageSample"
include(":app")
include(":core:model")
include(":core:network")
include(":core:data")
include(":core:common")
include(":core:datastore")
include(":core:domain")
