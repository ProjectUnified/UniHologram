package me.hsgamer.unihologram.spigot.plugin;

import me.hsgamer.unihologram.spigot.common.provider.SpigotHologramProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class UniHologramPlugin extends JavaPlugin {
    private SpigotHologramProvider provider;

    @Override
    public void onEnable() {
        this.provider = new SpigotHologramProvider(this);
    }

    public SpigotHologramProvider getProvider() {
        if (provider == null) {
            throw new IllegalStateException("Plugin is not enabled");
        }
        return provider;
    }
}
