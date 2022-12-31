package me.hsgamer.unihologram.spigot.common.hologram;

import me.hsgamer.unihologram.common.api.Hologram;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface CommonSpigotHologram extends Hologram<Location> {
    @NotNull
    default String colorize(@Nullable String string) {
        return string == null ? "" : ChatColor.translateAlternateColorCodes('&', string);
    }

    @NotNull
    default String decolorize(@Nullable String string) {
        return string == null ? "" : string.replace(ChatColor.COLOR_CHAR, '&');
    }
}
