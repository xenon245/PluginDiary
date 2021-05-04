package com.github.xenon.diary.plugin.process

import com.github.noonmaru.invfx.InvFX
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.scoreboard.Team

object Diary {
    var cowList = arrayListOf<DiaryCow>()
    var milk = HashMap<Player, Int>()
    var selectedMilk = HashMap<Team, String>()
    private val item1 = ItemStack(Material.MILK_BUCKET).apply {
        itemMeta = itemMeta.apply { this.displayName = "돌굴러가 낙농" }
    }
    private val item2 = ItemStack(Material.MILK_BUCKET).apply {
        itemMeta = itemMeta.apply { this.displayName = "C 유업" }
    }
    private val milkList = listOf(item1, item2)
    fun milkWindow() = InvFX.scene(1, "목장을 선택하세요") {
        panel(0, 0, 9, 1) {
            listView(0, 0, 9, 1) {

            }
            onInit {
                it.setItem(0, 0, item1)
                button(1, 0) {
                    onClick { b, e ->
                        e.isCancelled = true
                    }
                }
            }
        }
    }
}