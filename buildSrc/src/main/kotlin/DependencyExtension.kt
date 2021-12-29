//================================================ Dependency ================================================

fun getDependency(groupName: String, name: String): String {
    val group = Properties.dependencies[groupName] ?: throw DependencyGroupNotFoundException(groupName)
    val dependency = group[name] ?: throw DependencyNotFoundException(name)
    return if (dependency.contains("%version%")) {
        val version = Properties.versions[groupName] ?: throw NoVersionFoundException(groupName)
        dependency.replace("%version%", version)
    } else {
        dependency
    }
}

class DependencyNotFoundException(dependency: String) : Exception("Dependency: $dependency not Found")
class NoVersionFoundException(group: String) : Exception("There is not Version for Group: $group")
class DependencyGroupNotFoundException(group: String) : Exception("Cannot find dependency Group: $group")

//============================================================================================================


//=================================================== Git ====================================================

fun getCommitHash(): String = try {
    val runtime = Runtime.getRuntime()
    val process = runtime.exec("git rev-parse --short HEAD")
    val out = process.inputStream
    out.bufferedReader().readText().trim()
} catch (ignored: Exception) {
    "unknown"
}

//============================================================================================================

//============================================ Properties ====================================================
object Properties {

    @JvmStatic
    val group = "net.neverstopgaming"

    @JvmStatic
    val version = "1.0.0-SNAPSHOT"

    @JvmStatic
    val versions: MutableMap<String, String> = mutableMapOf<String, String>().also {
        it["kotlin"] = "1.5.10"
        it["simplecloud"] = "2.2.0"
        it["nsg"] = "1.0.0-SNAPSHOT"
    }

    @JvmStatic
    val dependencies: MutableMap<String, MutableMap<String, String>> =
        mutableMapOf<String, MutableMap<String, String>>().also {
            it["kotlin"] = mutableMapOf(
                Pair("stdlib", "org.jetbrains.kotlin:kotlin-stdlib:%version%"),
                Pair("serialization", "org.jetbrains.kotlin:kotlin-serialization:%version%")
            )
            it["google"] = mutableMapOf(
                "gson" to "com.google.code.gson:gson:2.8.6",
                "guice" to "com.google.inject:guice:5.0.1",
                "guava" to "com.google.guava:guava:30.1.1-jre",
                "guava-failureaccess" to "com.google.guava:failureaccess:1.0.1"
            )
            it["javax"] = mutableMapOf(
                "inject" to "javax.inject:javax.inject:1"
            )
            it["aopalliance"] = mutableMapOf(
                "aopalliance" to "aopalliance:aopalliance:1.0"
            )
            it["kotlinx"] = mutableMapOf(
                "coroutines-core" to "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3"
            )
            it["database"] = mutableMapOf(
                "mongo" to "org.mongodb:mongodb-driver-sync:4.3.0",
                "redis" to "io.lettuce:lettuce-core:6.1.0.RELEASE"
            )
            it["minecraft"] = mutableMapOf(
                "bukkit" to "com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT",
                "velocity" to "com.velocitypowered:velocity-api:3.0.1"
            )
            it["simplecloud"] = mutableMapOf(
                "api" to "eu.thesimplecloud.simplecloud:simplecloud-api:%version%",
                "base" to "eu.thesimplecloud.simplecloud:simplecloud-base:%version%",
                "plugin" to "eu.thesimplecloud.simplecloud:simplecloud-plugin:%version%",
            )
            it["components"] = mutableMapOf(
                "minimessage" to "net.kyori:adventure-text-minimessage:4.1.0-SNAPSHOT",
                "adventure" to "net.kyori:adventure-api:4.9.3"
            )
            it["nsg"] = mutableMapOf(
                "api" to "net.neverstopgaming.backend:nsg-api:%version%",
                "core" to "net.neverstopgaming.backend:nsg-core:%version%",
                "proxy" to "net.neverstopgaming.backend:nsg-proxy:%version%",
                "server" to "net.neverstopgaming.backend:nsg-server:%version%",
                "spigot" to "net.neverstopgaming.backend:nsg-server:%version%",
                "manager" to "net.neverstopgaming.backend:nsg-manager:%version%",
                "adapter" to "net.neverstopgaming.backend:nsg-adapter:%version%",
           )
           it["components"] = mutableMapOf(
               "minimessage" to "net.kyori:adventure-text-minimessage:4.1.0-SNAPSHOT",
               "adventure" to "net.kyori:adventure-api:4.9.3"
            )
        }


}
//============================================================================================================

//=========================================== Repositories ===================================================
object Repositories {
    const val MAVEN_CENTRAL = "https://repo1.maven.org/maven2/"
    const val MINECRAFT_SPIGOT = "https://hub.spigotmc.org/nexus/content/repositories/snapshots/"
    const val SONATYPE = "https://oss.sonatype.org/content/repositories/snapshots/"
    const val MINECRAFT_VELOCITY = "https://repo.velocitypowered.com/snapshots/"
    const val MINECRAFT = "https://libraries.minecraft.net/"
    const val MINECRAFT_PAPER = "https://papermc.io/repo/repository/maven-public/"
    const val DESTROYSTOKYO = "https://repo.destroystokyo.com/repository/maven-public/"
    const val SIMPLECLOUD = "https://repo.thesimplecloud.eu/artifactory/list/gradle-release-local/"
}
//============================================================================================================