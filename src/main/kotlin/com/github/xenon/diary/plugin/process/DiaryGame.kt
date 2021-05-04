package com.github.xenon.diary.plugin.process

import com.github.noonmaru.tap.Tap
import com.github.noonmaru.tap.entity.TapLivingEntity
import com.github.noonmaru.tap.packet.Packet
import com.github.xenon.diary.plugin.DiaryConfig
import com.github.xenon.diary.plugin.DiaryPlugin.Companion.instance
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Cow
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList.unregisterAll
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Team

class DiaryGame(val teams: List<Team>) {
    private var cowList = arrayListOf<DiaryCow>()
    init {
        Bukkit.getScoreboardManager().mainScoreboard.getObjective("DIARY")?.unregister()
        val diaryscore = Bukkit.getScoreboardManager().mainScoreboard.registerNewObjective("DIARY", "DIARY")
        teams.forEach { team ->
            team.players.forEach { p ->
                if(team.name == "RED") {
                    (p as Player).teleport(DiaryConfig.redLoc)
                } else if(team.name == "BLUE") {
                    (p as Player).teleport(DiaryConfig.blueLoc)
                } else if(team.name == "GREEN") {
                    (p as Player).teleport(DiaryConfig.greenLoc)
                } else if(team.name == "YELLOW") {
                    (p as Player).teleport(DiaryConfig.yellowLoc)
                }
            }
            diaryscore.displaySlot = DisplaySlot.SIDEBAR
            diaryscore.getScore(team.name).score = 1000
            for(x in -1..1) {
                for(z in -1..1) {
                    val location = DiaryConfig.cowLoc[team.name]!!
                    cowList.add(DiaryCow(Location(Bukkit.getWorlds().first(), location.x + 10 * x, location.y, location.z + 10 * z, location.yaw, location.pitch)))
                }
            }
        }
        Diary.cowList = cowList
        Bukkit.getScheduler().runTaskTimer(instance, DiaryScheduler(), 0L, 1L)
        Bukkit.getPluginManager().registerEvents(DiaryListener(), instance)
    }
    fun stopProcess() {
        unregisterAll()
        Bukkit.getScheduler().cancelTasks(instance)
        cowList.forEach { e ->
            e.destroy()
        }
    }
}