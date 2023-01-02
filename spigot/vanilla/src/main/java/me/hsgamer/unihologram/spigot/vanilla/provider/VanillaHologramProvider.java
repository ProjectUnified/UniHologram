package me.hsgamer.unihologram.spigot.vanilla.provider;

import me.hsgamer.unihologram.common.api.Hologram;
import me.hsgamer.unihologram.common.provider.LocalHologramProvider;
import me.hsgamer.unihologram.spigot.vanilla.hologram.VanillaHologram;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

/**
 * The hologram provider for Vanilla
 */
public class VanillaHologramProvider extends LocalHologramProvider<Location> {
    @Override
    protected @NotNull Hologram<Location> newHologram(@NotNull String name, @NotNull Location location) {
        return new VanillaHologram(name, location);
    }
}
