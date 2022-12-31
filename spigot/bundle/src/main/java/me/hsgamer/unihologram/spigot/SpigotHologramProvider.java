package me.hsgamer.unihologram.spigot;

import me.hsgamer.unihologram.common.api.Hologram;
import me.hsgamer.unihologram.common.hologram.NoneHologram;
import me.hsgamer.unihologram.spigot.common.provider.CommonSpigotHologramProvider;
import me.hsgamer.unihologram.spigot.decentholograms.provider.DHHologramProvider;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public class SpigotHologramProvider implements CommonSpigotHologramProvider {
    private final CommonSpigotHologramProvider provider;

    public SpigotHologramProvider() {
        if (DHHologramProvider.isAvailable()) {
            provider = new DHHologramProvider();
        } else {
            provider = (name, location) -> new NoneHologram(name);
        }
    }

    @Override
    public @NotNull Hologram createHologram(@NotNull String name, @NotNull Location location) {
        return provider.createHologram(name, location);
    }
}
