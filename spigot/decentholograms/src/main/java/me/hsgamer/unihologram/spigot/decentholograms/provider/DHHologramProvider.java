package me.hsgamer.unihologram.spigot.decentholograms.provider;

import me.hsgamer.unihologram.common.api.Hologram;
import me.hsgamer.unihologram.spigot.common.provider.CommonSpigotHologramProvider;
import me.hsgamer.unihologram.spigot.decentholograms.hologram.DHHologram;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public class DHHologramProvider implements CommonSpigotHologramProvider {
    public static boolean isAvailable() {
        return Bukkit.getPluginManager().isPluginEnabled("DecentHolograms");
    }

    @Override
    public @NotNull Hologram<Location> createHologram(@NotNull String name, @NotNull Location location) {
        return new DHHologram(name, location);
    }
}
