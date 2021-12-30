package net.neverstopgaming.haste.command

import eu.thesimplecloud.api.CloudAPI
import eu.thesimplecloud.api.command.ICommandSender
import eu.thesimplecloud.api.player.impl.CloudPlayer
import eu.thesimplecloud.api.player.text.CloudText
import eu.thesimplecloud.launcher.console.command.CommandType
import eu.thesimplecloud.launcher.console.command.ICommandHandler
import eu.thesimplecloud.launcher.console.command.annotations.Command
import eu.thesimplecloud.launcher.console.command.annotations.CommandSubPath
import net.neverstopgaming.haste.util.haste
import java.io.File

@Command("support", CommandType.CONSOLE_AND_INGAME, "cloud.support")
object SupportCommand : ICommandHandler {

    @CommandSubPath
    fun handle(sender: ICommandSender) {

        var msg = "\n\nJava version: ${System.getProperty("java.version")}\n"
        msg += "SimpleCloud version: ${File("storage/versions/lastStartedVersion.json").readText()}\n\n"
        msg += "Proxy versions: \n${
            CloudAPI.instance.getCloudServiceGroupManager().getProxyGroups()
                .joinToString { "\n   - ${it.getName()}: ${it.getServiceVersion().name}" }
        }\n"

        msg += "\nMinecraft versions: \n${
            CloudAPI.instance.getCloudServiceGroupManager().getServerGroups()
                .joinToString { "\n  - ${it.getName()}: ${it.getServiceVersion().name}" }
        }\n\n"
        msg += "Log file for Manger and Wrapper: \n\n"

        msg += "------------------------------------------------------\n\n"

        msg += "InternalWrapper logs: \n\n"
        msg += File("logs/simplecloud-log.0.1").readText()

        msg += "\n\n------------------------------------------------------\n\n"

        msg += "Manager logs: \n\n"
        msg += File("logs/simplecloud-log.0").readText()

        val haste = msg.haste()

        if (sender !is CloudPlayer) {
            sender.sendMessage(haste.url.toString())
            return
        }

        sender.sendMessage(
            CloudText("§8>> §7Haste: §b${haste.url}").addClickEvent(
                CloudText.ClickEventType.OPEN_URL,
                haste.url.toString()
            )
        )
    }
}