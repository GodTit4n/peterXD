/*
 * Copyright (c) 2023
 * Jason Hölzel | GodTitan
 */

package dev.godtitan.peter

import dev.godtitan.peter.command.PingCommand
import dev.godtitan.peter.command.TpsCommand
import dev.godtitan.peter.listener.AsyncChatListener
import dev.godtitan.peter.listener.PlayerJoinListener
import dev.godtitan.peter.listener.PlayerQuitListener
import dev.godtitan.peter.tablist.TablistManager
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory
import dev.rollczi.litecommands.bukkit.tools.BukkitOnlyPlayerContextual
import dev.rollczi.litecommands.bukkit.tools.BukkitPlayerArgument
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class Peter : JavaPlugin() {

    private lateinit var header: String
    private lateinit var footer: String
    lateinit var tablistManager: TablistManager

    override fun onEnable() {
        saveDefaultConfig()

        PlayerJoinListener(this)
        PlayerQuitListener(this)
        AsyncChatListener(this)

        LiteBukkitFactory.builder(server, server.name)
            .argument(Player::class.java, BukkitPlayerArgument(server, "player not found"))
            .contextualBind(Player::class.java, BukkitOnlyPlayerContextual("only players"))
            .commandInstance(PingCommand(this), TpsCommand(this))
            .register()

        this.tablistManager = TablistManager(this)
        this.header = ChatColor.translateAlternateColorCodes('&', config.getString("tab.header") ?: "")
        this.footer = ChatColor.translateAlternateColorCodes('&', config.getString("tab.footer") ?: "")

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, {
            Bukkit.getOnlinePlayers().forEach { player ->
                player.sendPlayerListHeaderAndFooter(
                    header.replace("#(user)", player.name)
                        .replace("#(tps)", String.format("%.2f", server.tps[0]))
                        .replace("#(ping)", player.ping.toString())
                        .toComponent(),
                    footer.replace("#(user)", player.name)
                        .replace("#(tps)", String.format("%.2f", server.tps[0]))
                        .replace("#(ping)", player.ping.toString())
                        .toComponent()
                )
            }
        }, 0, 20)
    }
}