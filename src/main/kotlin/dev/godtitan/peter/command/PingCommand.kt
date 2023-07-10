/*
 * Copyright (c) 2023
 * Jason Hölzel | GodTitan
 */

package dev.godtitan.peter.command

import dev.godtitan.peter.Peter
import dev.godtitan.peter.toComponent
import dev.rollczi.litecommands.command.execute.Execute
import dev.rollczi.litecommands.command.route.Route
import org.bukkit.ChatColor
import org.bukkit.entity.Player

@Route(name = "ping")
class PingCommand(peter: Peter) {

    private val message = peter.config.getString("messages.ping") ?: "messages.ping not found"

    @Execute(required = 0)
    fun ping(player: Player) {
        player.sendMessage(
            ChatColor.translateAlternateColorCodes(
                '&',
                message.replace("#(ping)", player.ping.toString())
            ).toComponent()
        )
    }
}