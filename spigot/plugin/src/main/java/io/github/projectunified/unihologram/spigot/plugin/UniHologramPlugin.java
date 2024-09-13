package io.github.projectunified.unihologram.spigot.plugin;

import io.github.projectunified.unihologram.api.HologramProvider;
import io.github.projectunified.unihologram.spigot.picker.SpigotHologramProviderPicker;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public class UniHologramPlugin extends JavaPlugin {
    private HologramProvider<Location> provider;

    @Override
    public void onEnable() {
        this.provider = new SpigotHologramProviderPicker(this).pick();
    }

    public HologramProvider<Location> getProvider() {
        if (provider == null) {
            throw new IllegalStateException("Plugin is not enabled");
        }
        return provider;
    }
}
