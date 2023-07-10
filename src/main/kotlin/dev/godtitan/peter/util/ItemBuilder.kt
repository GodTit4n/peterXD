/*
 * Copyright (c) 2023
 * Jason Hölzel | GodTitan
 */

package dev.godtitan.peter.util

import com.destroystokyo.paper.profile.ProfileProperty
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.LeatherArmorMeta
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.potion.PotionEffect
import java.util.*

class ItemBuilder {

    private val stack: ItemStack

    constructor() {
        stack = ItemStack(Material.AIR)
    }

    constructor(material: Material, amount: Int = 1) {
        stack = ItemStack(material, amount)
    }

    constructor(builder: ItemBuilder) {
        this.stack = builder.stack
    }

    constructor(stack: ItemStack) {
        this.stack = stack
    }

    fun type(type: Material) = apply {
        stack.type = type
    }

    fun amount(amount: Int) = apply {
        stack.amount = amount
    }

    fun displayName(displayName: Component) = apply {
        val meta = stack.itemMeta
        meta.displayName(displayName.decoration(TextDecoration.ITALIC, false))
        stack.itemMeta = meta
    }

    fun lore(vararg lore: Component): ItemBuilder {
        return lore(listOf(*lore))
    }

    fun lore(lore: List<Component>) = apply {
        val meta = stack.itemMeta
        meta.lore(lore.stream().map { it.decoration(TextDecoration.ITALIC, false) }.toList())
        stack.itemMeta = meta
    }

    fun lore(index: Int, component: Component) = apply {
        val meta = stack.itemMeta
        val lore = meta.lore() ?: return@apply
        lore[index] = component.decoration(TextDecoration.ITALIC, false)
        meta.lore(lore)
        stack.itemMeta = meta
    }

    fun owner(owner: String) = apply {
        if (stack.type != Material.PLAYER_HEAD) {
            throw IllegalArgumentException()
        }
        val meta = stack.itemMeta as SkullMeta
        meta.owningPlayer = Bukkit.getOfflinePlayer(owner)
        stack.itemMeta = meta
    }

    fun owner(owner: Player) = apply {
        if (stack.type != Material.PLAYER_HEAD) {
            throw IllegalArgumentException()
        }
        val meta = stack.itemMeta as SkullMeta
        meta.owningPlayer = owner
        stack.itemMeta = meta
    }

    fun profile(uuid: UUID?, name: String?, value: String, signature: String) = apply {
        if (stack.type != Material.PLAYER_HEAD) {
            throw IllegalArgumentException()
        }
        val meta = stack.itemMeta as SkullMeta
        val profile = Bukkit.createProfile(uuid, name)
        profile.setProperty(ProfileProperty("textures", value, signature))
        meta.playerProfile = profile
        stack.itemMeta = meta
    }

    fun itemFlag(vararg itemFlags: ItemFlag) = apply {
        val meta = stack.itemMeta
        meta.addItemFlags(*itemFlags)
        stack.itemMeta = meta
    }

    fun potion(potionEffect: PotionEffect) = apply {
        if (stack.type != Material.POTION && stack.type != Material.LINGERING_POTION && stack.type != Material.SPLASH_POTION) {
            throw IllegalArgumentException()
        }
        val meta = stack.itemMeta as PotionMeta
        meta.addCustomEffect(potionEffect, true)
        stack.itemMeta = meta
    }

    fun color(color: DyeColor): ItemBuilder {
        return color(color.color)
    }

    fun color(color: Color) = apply {
        when (stack.type) {
            Material.LEATHER_BOOTS, Material.LEATHER_LEGGINGS,
            Material.LEATHER_CHESTPLATE, Material.LEATHER_HELMET -> {
            }

            else -> throw IllegalArgumentException()
        }

        val meta = stack.itemMeta as LeatherArmorMeta
        meta.setColor(color)
        stack.itemMeta = meta
    }

    fun enchantment(enchantment: Enchantment, level: Int) = apply {
        stack.addUnsafeEnchantment(enchantment, level)
    }

    fun unbreakable(unbreakable: Boolean) = apply {
        val meta = stack.itemMeta
        meta.isUnbreakable = unbreakable
        stack.itemMeta = meta
    }

    fun build() = stack

    fun clone() = ItemBuilder(stack.clone())
}