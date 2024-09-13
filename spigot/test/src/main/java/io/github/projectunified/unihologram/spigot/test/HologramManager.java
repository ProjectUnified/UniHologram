package io.github.projectunified.unihologram.spigot.test;

import io.github.projectunified.unihologram.api.Hologram;
import io.github.projectunified.unihologram.api.HologramProvider;
import io.github.projectunified.unihologram.api.local.LocalHologramProvider;
import io.github.projectunified.unihologram.spigot.plugin.UniHologramPlugin;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class HologramManager {
    private final HologramProvider<Location> provider;
    private final HologramHandler handler;

    public HologramManager() {
        this.provider = JavaPlugin.getPlugin(UniHologramPlugin.class).getProvider();

        HologramHandler hologramHandler;
        if (provider instanceof LocalHologramProvider) {
            hologramHandler = new HologramHandler() {
                @Override
                public Hologram<Location> createHologram(String name, Location location) {
                    return provider.createHologram(name, location);
                }

                @Override
                public Collection<Hologram<Location>> getCreatedHolograms() {
                    return provider.getAllHolograms();
                }
            };
        } else {
            Map<String, Hologram<Location>> map = new HashMap<>();
            hologramHandler = new HologramHandler() {
                @Override
                public Hologram<Location> createHologram(String name, Location location) {
                    Hologram<Location> hologram = provider.createHologram(name, location);
                    map.put(name, hologram);
                    return hologram;
                }

                @Override
                public Collection<Hologram<Location>> getCreatedHolograms() {
                    return map.values();
                }
            };
        }
        this.handler = hologramHandler;
    }

    public boolean createHologram(String name, Location location) {
        Hologram<Location> existingHologram = getHologram(name);
        if (existingHologram != null) {
            if (existingHologram.isInitialized()) {
                return false;
            }
            existingHologram.init();
            existingHologram.setLocation(location);
        } else {
            handler.createHologram(name, location).init();
        }
        return true;
    }

    public HologramProvider<Location> getProvider() {
        return provider;
    }

    public void clearAll() {
        handler.getCreatedHolograms().forEach(Hologram::clear);
    }

    public Hologram<Location> getHologram(String name) {
        return provider.getHologram(name).orElse(null);
    }

    private interface HologramHandler {
        Hologram<Location> createHologram(String name, Location location);

        Collection<Hologram<Location>> getCreatedHolograms();
    }
}
