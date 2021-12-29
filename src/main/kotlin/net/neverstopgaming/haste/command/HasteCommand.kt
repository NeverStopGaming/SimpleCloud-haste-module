package net.neverstopgaming.haste.command

import eu.thesimplecloud.launcher.console.command.CommandType
import eu.thesimplecloud.launcher.console.command.ICommandHandler
import eu.thesimplecloud.launcher.console.command.annotations.Command
import eu.thesimplecloud.api.command.ICommandSender
import eu.thesimplecloud.api.player.impl.CloudPlayer
import eu.thesimplecloud.api.player.text.CloudText
import eu.thesimplecloud.api.service.ICloudService
import eu.thesimplecloud.launcher.console.command.annotations.CommandArgument
import eu.thesimplecloud.launcher.console.command.annotations.CommandSubPath
import eu.thesimplecloud.launcher.console.command.provider.ServiceCommandSuggestionProvider
import net.neverstopgaming.haste.util.haste
import java.io.File

@Command("haste", CommandType.CONSOLE_AND_INGAME)
object HasteCommand : ICommandHandler {

    @CommandSubPath("<service>")
    fun handle(
        sender: ICommandSender,
        @CommandArgument("service", ServiceCommandSuggestionProvider::class) service: ICloudService
    ) {

        val player = sender as CloudPlayer
        val logFile = File("tmp/${service.getName()}/logs/latest.log")
        val haste = logFile.readText().haste()
        player.sendMessage(
            CloudText("§8>> §7Haste: §b${haste.url}").addClickEvent(
                CloudText.ClickEventType.OPEN_URL,
                haste.url.toString()
            )
        )
    }
}