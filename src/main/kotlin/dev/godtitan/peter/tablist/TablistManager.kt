/*
 * Copyright (c) 2023
 * Jason Hölzel | GodTitan
 */

package dev.godtitan.peter.tablist

import dev.godtitan.peter.Peter
import dev.godtitan.peter.toComponent
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.scoreboard.Scoreboard

class TablistManager(private val peter: Peter) {

    lateinit var scoreboard: Scoreboard

    fun createTeam(player: Player) {
        if (player.scoreboard == Bukkit.getScoreboardManager().mainScoreboard) {
            player.scoreboard = Bukkit.getScoreboardManager().newScoreboard
        }

        scoreboard = player.scoreboard
        val teams = peter.config.getConfigurationSection("teams") ?: return

        teams.getKeys(false).forEach { key ->
            val team = scoreboard.registerNewTeam(key)
            team.prefix(
                ChatColor.translateAlternateColorCodes('&', peter.config.getString("messages.tab_prefix") ?: "")
                    .toComponent()
            )

            val players = peter.config.getStringList("teams.$team.players")

            players.forEach { _ ->
                if (players.contains(player.uniqueId.toString())) {
                    team.addEntry(player.name)
                }
            }
        }
    }

    fun addPlayer(player: Player, team: String) {
        val players = peter.config.getStringList("teams.$team.players")
        players.add(player.uniqueId.toString())

        peter.config.set("teams.$team.players", players)
        peter.saveConfig()
    }
}
