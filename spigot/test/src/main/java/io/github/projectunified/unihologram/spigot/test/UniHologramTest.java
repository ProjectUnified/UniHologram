package io.github.projectunified.unihologram.spigot.test;

import io.github.projectunified.unihologram.spigot.test.command.MainCommand;
import me.hsgamer.hscore.bukkit.baseplugin.BasePlugin;

public class UniHologramTest extends BasePlugin {
    private HologramManager hologramManager;

    @Override
    public void enable() {
        hologramManager = new HologramManager();
        getLogger().info("Provider: " + hologramManager.getProvider().getClass().getName());
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
