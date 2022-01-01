package net.neverstopgaming.haste

import eu.thesimplecloud.api.external.ICloudModule
import eu.thesimplecloud.launcher.startup.Launcher
import net.neverstopgaming.haste.command.HasteCommand
import net.neverstopgaming.haste.command.SupportCommand
import net.neverstopgaming.haste.util.Updater

const val pluginName = "SimpleCloud-Haste-Module"
const val pluginVersion = "1.0.1"

object HasteModule : ICloudModule {

    override fun onEnable() {
        Launcher.instance.commandManager.registerCommand(this, HasteCommand)
        Launcher.instance.commandManager.registerCommand(this, SupportCommand)

        Updater
    }

    override fun onDisable() {
    }

    override fun isReloadable() = true
}