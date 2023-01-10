package me.hsgamer.unihologram.spigot.protocollib.hologram;

import me.hsgamer.unihologram.common.api.Hologram;
import me.hsgamer.unihologram.common.api.HologramLine;
import me.hsgamer.unihologram.common.line.TextHologramLine;
import me.hsgamer.unihologram.spigot.common.hologram.extra.Colored;
import me.hsgamer.unihologram.spigot.common.hologram.extra.PlayerVisibility;
import me.hsgamer.unihologram.spigot.common.line.ItemHologramLine;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * The hologram for ProtocolLib
 */
public final class ProtocoLlibHologram implements Hologram<Location>, PlayerVisibility, Colored {

    private final List<HologramLine> lines = new ArrayList<>();

    @NotNull
    private final Plugin plugin;

    @NotNull
    private final String name;

    @NotNull
    private final Location location;

    /**
     * Create a new hologram
     *
     * @param plugin   the plugin
     * @param name     the name
     * @param location the location
     */
    public ProtocoLlibHologram(@NotNull final Plugin plugin, @NotNull final String name, @NotNull final Location location) {
        this.plugin = plugin;
        this.name = name;
        this.location = location;
    }

    @NotNull
    @Override
    public List<HologramLine> getLines() {
        return Collections.emptyList();
    }

    @Override
    public void setLines(@NotNull List<HologramLine> lines) {
    }

    @Override
    public void addLine(@NotNull HologramLine line) {
    }

    @Override
    public void insertLine(int index, @NotNull HologramLine line) {
    }

    @Override
    public void removeLine(int index) {
    }

    @Override
    public Optional<HologramLine> getLine(int index) {
        return Optional.empty();
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public void init() {
    }

    @Override
    public void clear() {
    }

    @Override
    public boolean isInitialized() {
        return false;
    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public void setLocation(Location location) {
    }

    @Override
    public boolean isVisible(Player viewer) {
        return false;
    }

    @Override
    public void showAll() {
    }

    @Override
    public void hideAll() {
    }

    @Override
    public void showTo(Player viewer) {
    }

    @Override
    public void hideTo(Player viewer) {
    }
}
