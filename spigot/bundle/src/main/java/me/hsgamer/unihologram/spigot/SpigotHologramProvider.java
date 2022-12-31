package me.hsgamer.unihologram.spigot;

import me.hsgamer.unihologram.common.api.Hologram;
import me.hsgamer.unihologram.common.hologram.NoneHologram;
import me.hsgamer.unihologram.spigot.common.provider.CommonSpigotHologramProvider;
import me.hsgamer.unihologram.spigot.decentholograms.provider.DHHologramProvider;
import me.hsgamer.unihologram.spigot.holographicdisplays.provider.HDHologramProvider;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * A hologram provider for Spigot.
 * It will use the best provider available.
 */
public class SpigotHologramProvider implements CommonSpigotHologramProvider {
    private final CommonSpigotHologramProvider provider;

    /**
     * Create a new hologram provider
     *
     * @param plugin the plugin
     */
    public SpigotHologramProvider(Plugin plugin) {
        if (DHHologramProvider.isAvailable()) {
            provider = new DHHologramProvider();
        } else if (HDHologramProvider.isAvailable()) {
            provider = new HDHologramProvider(plugin);
        } else {
            provider = NoneHologram::new;
        }
    }

    /**
     * Create a new hologram provider
     */
    public SpigotHologramProvider() {
        this(JavaPlugin.getProvidingPlugin(SpigotHologramProvider.class));
    }

    @Override
    public @NotNull Hologram<Location> createHologram(@NotNull String name, @NotNull Location location) {
        return provider.createHologram(name, location);
    }
}
