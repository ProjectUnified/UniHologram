package me.hsgamer.unihologram.spigot.plugin;

import me.hsgamer.unihologram.spigot.SpigotHologramProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.dependency.SoftDependency;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

@Plugin(name = PluginBuild.NAME, version = PluginBuild.VERSION)
@Description(PluginBuild.DESCRIPTION)
@Author(PluginBuild.AUTHOR)
@ApiVersion(ApiVersion.Target.v1_13)
@SoftDependency("DecentHolograms")
@SoftDependency("HolographicDisplays")
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
