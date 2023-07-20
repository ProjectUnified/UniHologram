package me.hsgamer.unihologram.spigot.folia.hologram;

import me.hsgamer.unihologram.common.hologram.SimpleHologram;
import me.hsgamer.unihologram.spigot.common.hologram.extra.Colored;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

/**
 * A simple hologram for Folia
 */
public class FoliaHologram extends SimpleHologram<Location> implements Colored {
    private final Plugin plugin;

    /**
     * Create a new hologram
     *
     * @param plugin   the plugin
     * @param name     the name of the hologram
     * @param location the location of the hologram
     */
    public FoliaHologram(Plugin plugin, String name, Location location) {
        super(name, location);
        this.plugin = plugin;
    }

    @Override
    protected void updateHologram() {

    }

    @Override
    protected void initHologram() {

    }

    @Override
    protected void clearHologram() {

    }
}
