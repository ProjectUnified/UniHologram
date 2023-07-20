package me.hsgamer.unihologram.spigot.vanilla.provider;

import me.hsgamer.unihologram.common.api.Hologram;
import me.hsgamer.unihologram.common.provider.LocalHologramProvider;
import me.hsgamer.unihologram.spigot.vanilla.hologram.VanillaHologram;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * The hologram provider for Vanilla
 */
public class VanillaHologramProvider extends LocalHologramProvider<Location> {
    private final Plugin plugin;

    /**
     * Create a new hologram provider
     *
     * @param plugin the plugin
     */
    public VanillaHologramProvider(Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Do some checks to make sure the provider can be used
     *
     * @return true if the provider can be used
     */
    public static boolean isAvailable() {
        // Disable if Folia is available
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            return false;
        } catch (ClassNotFoundException ignored) {
            // IGNORED
        }

        return true;
    }

    @Override
    protected @NotNull Hologram<Location> newHologram(@NotNull String name, @NotNull Location location) {
        return new VanillaHologram(plugin, name, location);
    }
}
