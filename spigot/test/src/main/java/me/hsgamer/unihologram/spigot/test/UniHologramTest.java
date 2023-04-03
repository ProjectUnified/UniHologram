package me.hsgamer.unihologram.spigot.test;

import me.hsgamer.hscore.bukkit.baseplugin.BasePlugin;
import me.hsgamer.unihologram.spigot.test.command.MainCommand;

public class UniHologramTest extends BasePlugin {
    private HologramManager hologramManager;

    @Override
    public void enable() {
        hologramManager = new HologramManager();
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
