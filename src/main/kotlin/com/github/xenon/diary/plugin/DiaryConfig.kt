package com.github.xenon.diary.plugin

import com.github.noonmaru.math.Vector
import com.github.noonmaru.tap.Tap
import com.github.noonmaru.tap.item.TapItem
import com.github.noonmaru.tap.item.TapItemStack
import com.google.common.collect.ImmutableList
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.configuration.ConfigurationSection
import java.util.ArrayList


/**
 * @author Nemo
 */
object DiaryConfig {
    var redLoc: Location? = null
    var blueLoc: Location? = null
    var greenLoc: Location? = null
    var yellowLoc: Location? = null
    var cowLoc = HashMap<String, Location>()
    var centerLoc: Location? = null
    fun load(config: ConfigurationSection) {
        redLoc = getLoc(config, "RED")
        blueLoc = getLoc(config, "BLUE")
        greenLoc = getLoc(config, "GREEN")
        yellowLoc = getLoc(config, "YELLOW")
        cowLoc["RED"] = getLoc(config, "REDCOW")
        cowLoc["BLUE"] = getLoc(config, "BLUECOW")
        cowLoc["GREEN"] = getLoc(config, "GREENCOW")
        cowLoc["YELLOW"] = getLoc(config, "YELLOWCOW")
        centerLoc = getLoc(config, "CENTER")
    }

    private fun getTeamConfigs(config: ConfigurationSection, key: String): List<TeamConfig> {
        val configs: MutableList<TeamConfig> = ArrayList()
        val teamConfig = config.getConfigurationSection(key)
        for ((name, value) in teamConfig.getValues(false)) {
            val info = value as ConfigurationSection
            val prefix = info.getString("prefix")
            val entries = info.getStringList("entries")
            configs.add(TeamConfig(name, prefix, entries))
        }
        return ImmutableList.copyOf(configs)
    }

    private fun getVec(config: ConfigurationSection, key: String): Vector {
        val vecConfig = config.getConfigurationSection(key)
        val x = vecConfig.getDouble("x")
        val y = vecConfig.getDouble("y")
        val z = vecConfig.getDouble("z")
        return Vector(x, y, z)
    }

    private fun getItems(config: ConfigurationSection, key: String): List<TapItemStack> {
        val itemNames = config.getStringList(key)
        val items: MutableList<TapItemStack> = ArrayList(itemNames.size)
        for (itemName in itemNames) {
            val item = Tap.ITEM.getItem(itemName)
            if (item != null) items.add(Tap.ITEM.newItemStack(item, 1, 0))
        }
        return items
    }

    private fun getLoc(config: ConfigurationSection, key: String): Location {
        val locConfig = config.getConfigurationSection(key)
        val world = Bukkit.getWorld(locConfig.getString("world"))
        val x = locConfig.getDouble("x")
        val y = locConfig.getDouble("y")
        val z = locConfig.getDouble("z")
        val yaw = locConfig.getDouble("yaw").toFloat()
        val pitch = locConfig.getDouble("pitch").toFloat()
        return Location(world, x, y, z, yaw, pitch)
    }

    class TeamConfig(val name: String, val prefix: String, entries: Collection<String?>?) {
        val entries = LinkedHashSet<String?>()

        init {
            this.entries.addAll(entries!!)
        }
    }
}