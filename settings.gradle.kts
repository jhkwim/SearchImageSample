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

rootProject.name = "KKO"
include(":app")
include(":feature:favorites")
include(":feature:search")
include(":core:model")
include(":core:domain")
include(":core:network")
include(":core:data")
include(":core:common")
include(":core:datastore")
