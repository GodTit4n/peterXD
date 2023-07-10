/*
 * Copyright (c) 2023
 * Jason Hölzel | GodTitan
 */

package dev.godtitan.peter.listener

import dev.godtitan.peter.Peter
import dev.godtitan.peter.toComponent
import dev.godtitan.peter.toLegacyString
import io.papermc.paper.event.player.AsyncChatEvent
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class AsyncChatListener(private val peter: Peter) : Listener {

    private val message: String

    init {
        Bukkit.getPluginManager().registerEvents(this, peter)
        message = ChatColor.translateAlternateColorCodes(
            '&',
            peter.config.getString("messages.player_chat") ?: "&7#(user)&8: &f#(message)"
        )
    }

    @EventHandler
    fun onChat(event: AsyncChatEvent) {
        event.isCancelled = true
        Bukkit.broadcast(
            message.replace("#(user)", event.player.name).replace("#(message)", event.message().toLegacyString())
                .toComponent()
        )
    }
}