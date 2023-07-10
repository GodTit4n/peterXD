/*
 * Copyright (c) 2023
 * Jason Hölzel | GodTitan
 */

package dev.godtitan.peter

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer

fun Component.toLegacyString(): String {
    return LegacyComponentSerializer.legacyAmpersand().serialize(this)
}

fun String.toComponent(): Component {
    return Component.text(this)
}