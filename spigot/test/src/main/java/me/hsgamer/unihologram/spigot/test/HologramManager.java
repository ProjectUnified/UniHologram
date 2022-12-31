package me.hsgamer.unihologram.spigot.test;

import me.hsgamer.unihologram.common.api.Hologram;
import me.hsgamer.unihologram.spigot.SpigotHologramProvider;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

public class HologramManager {
    private final SpigotHologramProvider spigotHologramProvider;
    private final Map<String, Hologram<Location>> hologramMap = new HashMap<>();

    public HologramManager(UniHologramPlugin plugin) {
        this.spigotHologramProvider = new SpigotHologramProvider(plugin);
    }

    public void createHologram(String name, Location location) {
        Hologram<Location> hologram = spigotHologramProvider.createHologram(name, location);
        hologram.init();
        hologramMap.put(name, hologram);
    }

    public void removeHologram(String name) {
        Hologram<Location> hologram = hologramMap.remove(name);
        if (hologram != null) {
            hologram.clear();
        }
    }

    public void clearAll() {
        hologramMap.values().forEach(Hologram::clear);
        hologramMap.clear();
    }

    public Hologram<Location> getHologram(String name) {
        return hologramMap.get(name);
    }
}
