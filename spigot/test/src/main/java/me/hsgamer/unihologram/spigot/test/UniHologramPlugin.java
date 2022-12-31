package me.hsgamer.unihologram.spigot.test;

import me.hsgamer.hscore.bukkit.baseplugin.BasePlugin;
import me.hsgamer.unihologram.spigot.test.command.MainCommand;
import org.bukkit.plugin.java.annotation.dependency.SoftDependency;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Plugin;

@Plugin(name = "UniHologramTest", version = "1.0.0")
@ApiVersion(ApiVersion.Target.v1_13)
@SoftDependency("DecentHolograms")
@SoftDependency("HolographicDisplays")
public class UniHologramPlugin extends BasePlugin {
    private HologramManager hologramManager;

    @Override
    public void enable() {
        hologramManager = new HologramManager(this);
        registerCommand(new MainCommand(this));
    }

    @Override
    public void disable() {
        hologramManager.clearAll();
    }

    public HologramManager getHologramManager() {
        return hologramManager;
    }
}
