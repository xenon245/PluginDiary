package com.github.xenon.diary.plugin.process

import com.github.noonmaru.invfx.openWindow
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.scoreboard.Team

class DiaryCommand : CommandExecutor, TabCompleter {
    var game: DiaryGame? = null
    override fun onCommand(p0: CommandSender, p1: Command, p2: String, p3: Array<out String>): Boolean {
        when(p3[0]) {
            "start" -> {
                when(p3[1]) {
                    "Diary" -> {
                        val teams = arrayListOf<Team>()
                        for(i in 2 until p3.count()) {
                            val name = p3[i]
                            val team = Bukkit.getScoreboardManager().mainScoreboard.getTeam(name)
                            if(team == null) {
                                p0.sendMessage("Not found team : $team")
                                return true
                            }
                            teams += team
                        }
                        if(game == null) {
                            game = DiaryGame(teams)
                        } else {
                            p0.sendMessage("Game is not Null!")
                        }
                    }
                }
            }
            "stop" -> {
                if(game != null) {
                    game?.stopProcess()
                    game = null
                } else {
                    p0.sendMessage("Game is not on Process!")
                }
            }
            "window" ->
                (p0 as Player).openWindow(Diary.milkWindow())
        }
        return true
    }

    override fun onTabComplete(
        p0: CommandSender,
        p1: Command,
        p2: String,
        p3: Array<out String>
    ) = listOf("start", "stop")
}