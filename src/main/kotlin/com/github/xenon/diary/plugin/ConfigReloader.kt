package com.github.xenon.diary.plugin

import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

class ConfigReloader(val file: File) : Runnable {
    private val file2: File
    private var lastModified: Long = 0
    init {
        this.file2 = file
    }
    override fun run() {
        val last = file2.lastModified()
        if(last != this.lastModified) {
            lastModified = last
            val config = YamlConfiguration.loadConfiguration(file2)
            DiaryConfig.load(config)
            println("Config reloaded!")
        }
    }
}