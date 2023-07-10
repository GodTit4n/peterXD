/*
 * Copyright (c) 2023
 * Jason Hölzel | GodTitan
 */

package dev.godtitan.peter.listener

import dev.godtitan.peter.Peter
import dev.godtitan.peter.toComponent
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoinListener(private val peter: Peter) : Listener {

    private val joinMessage: String

    init {
        Bukkit.getPluginManager().registerEvents(this, peter)
        joinMessage = ChatColor.translateAlternateColorCodes('&', peter.config.getString("messages.join") ?: "")
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        event.joinMessage(joinMessage.replace("#(user)", player.name).toComponent())
        peter.tablistManager.createTeam(player)

    }
}