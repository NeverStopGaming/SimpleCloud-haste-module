package net.neverstopgaming.haste

import eu.thesimplecloud.api.external.ICloudModule
import eu.thesimplecloud.launcher.startup.Launcher
import net.neverstopgaming.haste.command.HasteCommand

class HasteModule : ICloudModule {

    override fun onEnable() {
        Launcher.instance.commandManager.registerCommand(this, HasteCommand)
    }

    override fun onDisable() {
    }

    override fun isReloadable() = true
}