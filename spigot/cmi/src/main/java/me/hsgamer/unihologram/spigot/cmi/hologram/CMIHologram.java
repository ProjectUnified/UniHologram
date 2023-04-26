package me.hsgamer.unihologram.spigot.cmi.hologram;

import com.Zrips.CMI.CMI;
import com.google.common.base.Preconditions;
import me.hsgamer.unihologram.common.api.Hologram;
import me.hsgamer.unihologram.common.api.HologramLine;
import me.hsgamer.unihologram.common.line.TextHologramLine;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * The hologram for CMI
 */
public class CMIHologram implements Hologram<Location> {
    private final Supplier<com.Zrips.CMI.Modules.Holograms.CMIHologram> hologramSupplier;
    private final String name;
    private com.Zrips.CMI.Modules.Holograms.CMIHologram hologram;

    /**
     * Create a new hologram
     *
     * @param name     the name of the hologram
     * @param location the location of the hologram
     */
    public CMIHologram(String name, Location location) {
        this.name = name;
        this.hologramSupplier = () -> {
            com.Zrips.CMI.Modules.Holograms.CMIHologram hologram = new com.Zrips.CMI.Modules.Holograms.CMIHologram(name, location);
            CMI.getInstance().getHologramManager().addHologram(hologram);
            hologram.update();
            return hologram;
        };
    }

    /**
     * Create a new hologram
     *
     * @param hologram the hologram
     */
    public CMIHologram(com.Zrips.CMI.Modules.Holograms.CMIHologram hologram) {
        this(hologram.getName(), hologram.getCenterLocation());
        this.hologram = hologram;
    }

    private void checkHologramInitialized() {
        Preconditions.checkNotNull(hologram, "Hologram is not initialized");
    }

    @Override
    public @NotNull List<HologramLine> getLines() {
        checkHologramInitialized();
        return hologram.getLines().stream().map(TextHologramLine::new).collect(Collectors.toList());
    }

    @Override
    public void setLines(@NotNull List<HologramLine> lines) {
        checkHologramInitialized();
        hologram.setLines(lines.stream().map(HologramLine::getRawContent).collect(Collectors.toList()));
        hologram.refresh();
    }

    @Override
    public void addLine(@NotNull HologramLine line) {
        checkHologramInitialized();
        hologram.addLine(line.getRawContent());
        hologram.refresh();
    }

    @Override
    public void setLine(int index, @NotNull HologramLine line) {
        checkHologramInitialized();
        hologram.setLine(index, line.getRawContent());
        hologram.refresh();
    }

    @Override
    public void insertLine(int index, @NotNull HologramLine line) {
        checkHologramInitialized();
        List<HologramLine> lines = new ArrayList<>(getLines());
        lines.add(index, line);
        setLines(lines);
    }

    @Override
    public void removeLine(int index) {
        checkHologramInitialized();
        hologram.removeLine(index);
        hologram.refresh();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void init() {
        hologram = hologramSupplier.get();
    }

    @Override
    public void clear() {
        if (hologram != null) {
            hologram.remove();
            hologram = null;
        }
    }

    @Override
    public boolean isInitialized() {
        return hologram != null;
    }

    @Override
    public Location getLocation() {
        return hologram.getCenterLocation();
    }

    @Override
    public void setLocation(Location location) {
        checkHologramInitialized();
        hologram.setLoc(location);
        hologram.refresh();
    }
}
