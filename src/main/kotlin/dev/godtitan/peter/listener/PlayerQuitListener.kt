/*
 * Copyright (c) 2023
 * Jason Hölzel | GodTitan
 */

package dev.godtitan.peter.listener

import dev.godtitan.peter.Peter
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class PlayerQuitListener(peter: Peter) : Listener {

    private val quitMessage: String

    init {
        Bukkit.getPluginManager().registerEvents(this, peter)
        quitMessage = ChatColor.translateAlternateColorCodes('&', peter.config.getString("messages.quit") ?: "")
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerQuitEvent) {
        event.quitMessage(Component.text(quitMessage.replace("#(user)", event.player.name)))
    }
}