package net.neverstopgaming.haste.command

import eu.thesimplecloud.api.command.ICommandSender
import eu.thesimplecloud.api.player.impl.CloudPlayer
import eu.thesimplecloud.api.player.text.CloudText
import eu.thesimplecloud.api.service.ICloudService
import eu.thesimplecloud.launcher.console.command.CommandType
import eu.thesimplecloud.launcher.console.command.ICommandHandler
import eu.thesimplecloud.launcher.console.command.annotations.Command
import eu.thesimplecloud.launcher.console.command.annotations.CommandArgument
import eu.thesimplecloud.launcher.console.command.annotations.CommandSubPath
import eu.thesimplecloud.launcher.console.command.provider.ServiceCommandSuggestionProvider
import eu.thesimplecloud.launcher.console.command.provider.WrapperCommandSuggestionProvider
import eu.thesimplecloud.launcher.startup.Launcher
import net.neverstopgaming.haste.util.haste
import java.util.stream.Collectors

@Command("haste", CommandType.CONSOLE_AND_INGAME, "cloud.haste", ["paste", "log"])
object HasteCommand : ICommandHandler {

    @CommandSubPath("current")
    fun handle(sender: ICommandSender) {

        if (sender !is CloudPlayer) {
            sender.sendMessage("&cYou must be a player to use this command!")
            return
        }
        sender.getConnectedServer()?.let { haste(sender, it.getName()) }
    }

    @CommandSubPath("<service>")
    fun handle(
        sender: ICommandSender,
        @CommandArgument("service", ServiceCommandSuggestionProvider::class) service: ICloudService
    ) {
        haste(sender, service.getName())
    }

    @CommandSubPath("wrapper <wrapper>")
    fun handle(
        sender: ICommandSender,
        @CommandArgument("wrapper", WrapperCommandSuggestionProvider::class) wrapper: String,
    ) {
        haste(sender, wrapper)
    }

    private fun haste(sender: ICommandSender, service: String) {

        val screen = Launcher.instance.screenManager.getScreen(service)
        if (screen == null) {
            sender.sendMessage("Service screen is null")
            return
        }
        val screenMessages = screen.getAllSavedMessages()
        if (screenMessages.isEmpty()) {
            sender.sendMessage("Service screen is empty")
            return
        }
        val msg = screenMessages.parallelStream().collect(Collectors.joining("\n"))

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