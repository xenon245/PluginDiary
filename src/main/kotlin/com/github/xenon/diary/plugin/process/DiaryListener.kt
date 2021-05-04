package com.github.xenon.diary.plugin.process

import com.github.noonmaru.math.Vector
import com.github.noonmaru.tap.Tap
import com.github.noonmaru.tap.math.BoundingBox
import com.github.xenon.diary.plugin.DiaryConfig
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class DiaryListener : Listener {
    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        if(event.item.type == Material.BUCKET) {
            val loc = event.player.location
            val v = loc.direction
            val from = Vector(loc.x, loc.y, loc.z)
            val to = Vector(v.x, v.y, v.z).multiply(100.0).add(from)
            val box = Tap.MATH.newBoundingBox(DiaryConfig.centerLoc!!.x + 50, DiaryConfig.centerLoc!!.y, DiaryConfig.centerLoc!!.z + 50, DiaryConfig.centerLoc!!.x - 50, DiaryConfig.centerLoc!!.y, DiaryConfig.centerLoc!!.z - 50)
            val result = Tap.MATH.newRayTraceCalculator(from, to).calculate(box)
            if(result != null) {
                var found: DiaryCow? = null
                var ds = 0.0
                for(cow in Diary.cowList) {
                    val hashSet = HashSet<Material>()
                    hashSet += Material.BUCKET
                    val cds = cow.getLoc().distance(Location(Bukkit.getWorlds().first(), result.x, result.y, result.z))
                    if(ds == 0.0 || cds < ds) {
                        found = cow
                        ds = cds
                    }
                }
                found?.minusScale()
                Diary.milk[event.player] = Diary.milk[event.player]!! + 1
            }
        }
    }
}