package me.hsgamer.unihologram.spigot.fancyholograms.hologram;

import com.google.common.base.Preconditions;
import de.oliver.fancyholograms.FancyHologramsPlugin;
import de.oliver.fancyholograms.api.Hologram;
import de.oliver.fancyholograms.api.HologramData;
import me.hsgamer.unihologram.common.api.HologramLine;
import me.hsgamer.unihologram.common.line.TextHologramLine;
import me.hsgamer.unihologram.spigot.common.hologram.extra.PlayerVisibility;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * The hologram for FancyHolograms
 */
public class FHHologram implements me.hsgamer.unihologram.common.api.Hologram<Location>, PlayerVisibility {
    private static final double LINE_HEIGHT = 0.25;
    private final Hologram hologram;

    /**
     * Create a new hologram
     *
     * @param name     the name of the hologram
     * @param location the location of the hologram
     */
    public FHHologram(String name, Location location) {
        HologramData data = new HologramData(name);
        data.setLocation(location);
        this.hologram = FancyHologramsPlugin.get().getHologramsManager().create(data);
    }

    /**
     * Create a new hologram
     *
     * @param hologram the hologram
     */
    public FHHologram(Hologram hologram) {
        this.hologram = hologram;
    }

    private static Location getTopLocation(Location location, float scale, int lineCount) {
        Location newLocation = Objects.requireNonNull(location).clone();
        newLocation.setY(newLocation.getY() + LINE_HEIGHT * scale * lineCount);
        return newLocation;
    }

    private static Location getBottomLocation(Location location, float scale, int lineCount) {
        Location newLocation = Objects.requireNonNull(location).clone();
        newLocation.setY(newLocation.getY() - LINE_HEIGHT * scale * lineCount);
        return newLocation;
    }

    private Location getTopLocation() {
        return getTopLocation(hologram.getData().getLocation(), hologram.getData().getScale(), hologram.getData().getText().size());
    }

    private void setTopLocation(Location location) {
        hologram.getData().setLocation(getBottomLocation(location, hologram.getData().getScale(), hologram.getData().getText().size()));
    }

    private void updateTopLocation(int lineCount) {
        Location topLocation = getTopLocation(hologram.getData().getLocation(), hologram.getData().getScale(), hologram.getData().getText().size());
        Location bottomLocation = getBottomLocation(topLocation, hologram.getData().getScale(), lineCount);
        hologram.getData().setLocation(bottomLocation);
    }

    private void checkHologramInitialized() {
        Preconditions.checkArgument(isInitialized(), "Hologram is not initialized");
    }

    private void updateHologram() {
        hologram.updateHologram();
        FancyHologramsPlugin.get().getHologramsManager().refreshHologramForPlayersInWorld(hologram);
    }

    @Override
    public @NotNull List<HologramLine> getLines() {
        checkHologramInitialized();
        return hologram.getData().getText().stream().<HologramLine>map(TextHologramLine::new).collect(Collectors.toList());
    }

    @Override
    public void setLines(@NotNull List<HologramLine> lines) {
        checkHologramInitialized();
        hologram.getData().setText(lines.stream().map(HologramLine::getRawContent).collect(Collectors.toList()));
        updateHologram();
    }

    private void editLine(Consumer<List<HologramLine>> consumer) {
        checkHologramInitialized();
        List<HologramLine> lines = new ArrayList<>(getLines());
        consumer.accept(lines);
        updateTopLocation(lines.size());
        setLines(lines);
    }

    @Override
    public void addLine(@NotNull HologramLine line) {
        editLine(lines -> lines.add(line));
    }

    @Override
    public void insertLine(int index, @NotNull HologramLine line) {
        editLine(lines -> lines.add(index, line));
    }

    @Override
    public void removeLine(int index) {
        editLine(lines -> lines.remove(index));
    }

    @Override
    public String getName() {
        return hologram.getData().getName();
    }

    @Override
    public void init() {
        hologram.createHologram();
        hologram.showHologram(Bukkit.getOnlinePlayers());
        FancyHologramsPlugin.get().getHologramsManager().addHologram(hologram);
    }

    @Override
    public void clear() {
        hologram.hideHologram(Bukkit.getOnlinePlayers());
        hologram.deleteHologram();
        FancyHologramsPlugin.get().getHologramsManager().removeHologram(hologram);
    }

    @Override
    public boolean isInitialized() {
        return FancyHologramsPlugin.get().getHologramsManager().getHolograms().contains(hologram);
    }

    @Override
    public Location getLocation() {
        checkHologramInitialized();
        return getTopLocation();
    }

    @Override
    public void setLocation(Location location) {
        checkHologramInitialized();
        setTopLocation(location);
        updateHologram();
    }

    @Override
    public boolean isVisible(Player viewer) {
        checkHologramInitialized();
        return hologram.isShown(viewer);
    }

    @Override
    public void showAll() {
        checkHologramInitialized();
        hologram.showHologram(Bukkit.getOnlinePlayers());
    }

    @Override
    public void hideAll() {
        checkHologramInitialized();
        hologram.hideHologram(Bukkit.getOnlinePlayers());
    }

    @Override
    public void showTo(Player viewer) {
        checkHologramInitialized();
        hologram.showHologram(viewer);
    }

    @Override
    public void hideTo(Player viewer) {
        checkHologramInitialized();
        hologram.hideHologram(viewer);
    }
}
