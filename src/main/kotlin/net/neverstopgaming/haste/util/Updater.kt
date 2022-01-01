package net.neverstopgaming.haste.util

import eu.thesimplecloud.launcher.startup.Launcher
import net.neverstopgaming.haste.pluginName
import net.neverstopgaming.haste.pluginVersion
import java.io.File
import java.net.URI
import java.net.URL
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.logging.Level


object Updater {

    private const val updateServer = "https://update.neverstopgaming.net"
    private const val jenkinsServer = "https://ci.neverstopgaming.net"

    private val request =
        HttpRequest.newBuilder(URI("$updateServer/update?plugin=$pluginName")).GET().build()
    private val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

    data class UpdateResponse(
        val version: String,
    )

    init {

        if (response.statusCode() == 200) {

            val version = gson.fromJson(response.body(), UpdateResponse::class.java).version

            if ("version" == pluginVersion) {

                Launcher.instance.logger.info("$pluginName is outdated! New version: $version (Current version: $pluginVersion)")

                File("modules/$pluginName-$pluginVersion.jar").delete()

                val inputStream =
                    URL("$jenkinsServer/job/$pluginName/lastSuccessfulBuild/artifact/build/libs/$pluginName-$version.jar").openStream()
                Files.copy(
                    inputStream,
                    Paths.get("modules/$pluginName-$version.jar"),
                    StandardCopyOption.REPLACE_EXISTING
                )
            }

        } else {
            Launcher.instance.logger.log(
                Level.WARNING,
                "Failed to check for updates! Status code: ${response.statusCode()}"
            )
        }
    }
}