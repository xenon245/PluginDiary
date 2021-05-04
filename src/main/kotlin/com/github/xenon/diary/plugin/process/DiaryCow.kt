package com.github.xenon.diary.plugin.process

import com.github.noonmaru.customentity.CustomEntityPacket
import com.github.noonmaru.tap.Tap
import com.github.noonmaru.tap.entity.TapLivingEntity
import com.github.noonmaru.tap.firework.FireworkEffect
import com.github.noonmaru.tap.packet.Packet
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Cow

class DiaryCow(location: Location) {
    var size: Float = 1.0F
    private var ticks = 0
    val entity: TapLivingEntity
    init {
        entity = Tap.ENTITY.createEntity<TapLivingEntity>(Cow::class.java).apply {
            setPositionAndRotation(location.x, location.y, location.z, location.yaw, location.pitch)
        }
        Packet.ENTITY.spawnMob(entity.bukkitEntity).sendAll()
        Packet.ENTITY.metadata(entity.bukkitEntity).sendAll()
        Packet.ENTITY.teleport(entity.bukkitEntity, entity.posX, entity.posY, entity.posZ, entity.yaw, entity.pitch, true)
    }
    fun destroy() {
        Packet.ENTITY.destroy(entity.id).sendAll()
    }
    fun getLoc() : Location {
        return Location(Bukkit.getWorlds().first(), entity.posX, entity.posY, entity.posZ, entity.yaw, entity.pitch)
    }
    fun updateScale() {
        ticks++
        if(ticks > 30) {
            size += 0.1F
            ticks = 0
        }
        if(size > 2.0F) {
            val fireworkEffect = FireworkEffect.builder().color(0xFFFFFF).type(FireworkEffect.Type.BURST).build()
            Packet.EFFECT.firework(fireworkEffect, entity.posX, entity.posY, entity.posZ).sendAll()
            Packet.ENTITY.destroy(entity.id).sendAll()
        } else if(size <= 0.1) {
            val fireworkEffect = FireworkEffect.builder().color(0xFFFFFF).type(FireworkEffect.Type.BURST).build()
            Packet.EFFECT.firework(fireworkEffect, entity.posX, entity.posY, entity.posZ).sendAll()
            Packet.ENTITY.destroy(entity.id).sendAll()
        }
        else {
            CustomEntityPacket.register(entity.id).sendAll()
            CustomEntityPacket.scale(entity.id, size, size, size, 5).sendAll()
        }
    }
    fun minusScale() {
        size -= 0.2F
        if(size >= 2.0F) {
            val fireworkEffect = FireworkEffect.builder().color(0xFFFFFF).type(FireworkEffect.Type.BURST).build()
            Packet.EFFECT.firework(fireworkEffect, entity.posX, entity.posY, entity.posZ).sendAll()
            Packet.ENTITY.destroy(entity.id).sendAll()
        } else if(size <= 0.1) {
            val fireworkEffect = FireworkEffect.builder().color(0xFFFFFF).type(FireworkEffect.Type.BURST).build()
            Packet.EFFECT.firework(fireworkEffect, entity.posX, entity.posY, entity.posZ).sendAll()
            Packet.ENTITY.destroy(entity.id).sendAll()
        }
        else {
            CustomEntityPacket.register(entity.id).sendAll()
            CustomEntityPacket.scale(entity.id, size, size, size, 5).sendAll()
        }
    }
    fun getScale() : Float {
        return size
    }
}