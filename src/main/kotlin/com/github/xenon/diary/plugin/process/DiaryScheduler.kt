package com.github.xenon.diary.plugin.process

import com.github.xenon.diary.plugin.DiaryPlugin.Companion.instance
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryView

class DiaryScheduler : Runnable {
    private var ticks = 0
    private var minute = 1
    private var second = 0
    override fun run() {
        Bukkit.getOnlinePlayers().forEach { p ->
            if(Diary.milk[p] == null) {
                Diary.milk[p] = 0
            }
            if(p.itemInHand.type == Material.BUCKET) {
                p.sendTitle("우유", Diary.milk[p].toString(), 0, 3, 0)
            }
        }
        Diary.cowList.forEach { c ->
            if(c.size >= 2.0) {
                Diary.cowList.remove(c)
            } else if(c.size <= 0.1) {
                Diary.cowList.remove(c)
            }
            c.updateScale()
        }
        ticks++
        if(ticks == 20) {
            second--
        }
        if(second == -1) {
            minute--
            second = 59
            if(minute == -1) {
                Bukkit.getOnlinePlayers().forEach { p ->

                }
            }
        }
    }
}