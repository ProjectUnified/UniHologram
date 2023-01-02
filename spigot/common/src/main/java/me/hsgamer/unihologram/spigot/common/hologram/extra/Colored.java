package me.hsgamer.unihologram.spigot.common.hologram.extra;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The common hologram for Spigot
 */
public interface Colored {
    @NotNull
    default String colorize(@Nullable String string) {
        return string == null ? "" : ChatColor.translateAlternateColorCodes('&', string);
    }

    @NotNull
    default String decolorize(@Nullable String string) {
        return string == null ? "" : string.replace(ChatColor.COLOR_CHAR, '&');
    }
}
