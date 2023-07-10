/*
 * Copyright (c) 2023
 * Jason Hölzel | GodTitan
 */

package dev.godtitan.peter.command

import dev.godtitan.peter.Peter
import dev.rollczi.litecommands.command.execute.Execute
import dev.rollczi.litecommands.command.route.Route
import org.bukkit.ChatColor
import org.bukkit.entity.Player

@Route(name = "tps", aliases = ["lag"])
class TpsCommand(private val peter: Peter) {

    private val message = peter.config.getString("messages.tps") ?: "messages.tps not found"

    @Execute(required = 0)
    fun tps(player: Player) {
        player.sendMessage(
            ChatColor.translateAlternateColorCodes(
                '&',
                message
                    .replace("#(tps1)", String.format("%.2f", peter.server.tps[0]))
                    .replace("#(tps5)", String.format("%.2f", peter.server.tps[1]))
                    .replace("#(tps15)", String.format("%.2f", peter.server.tps[2]))
            )
        )
    }
}