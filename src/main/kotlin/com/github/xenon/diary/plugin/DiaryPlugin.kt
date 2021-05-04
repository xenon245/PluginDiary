package com.github.xenon.diary.plugin

import com.github.xenon.diary.plugin.process.DiaryCommand
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class DiaryPlugin : JavaPlugin() {
    companion object {
        lateinit var instance: DiaryPlugin
    }
    override fun onEnable() {
        saveDefaultConfig()
        server.scheduler.runTaskTimer(this, ConfigReloader(File(dataFolder, "config.yml")), 0L, 1L)
        instance = this
        getCommand("game").executor = DiaryCommand()
        getCommand("game").tabCompleter = DiaryCommand()
    }
}