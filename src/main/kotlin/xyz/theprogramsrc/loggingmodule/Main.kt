package xyz.theprogramsrc.loggingmodule

import xyz.theprogramsrc.simplecoreapi.global.models.module.Module
import xyz.theprogramsrc.simplecoreapi.global.models.module.ModuleDescription

class FilesModule: Module {

    override val description: ModuleDescription =
        ModuleDescription(
            name = "@name@",
            version = "@version@",
            authors = listOf("Im-Fran")
        )

    override fun onEnable() {
        // Do nothing
    }

    override fun onDisable() {
        // Do nothing
    }
}