package io.github.projectunified.unihologram.spigot.cmi;

import com.Zrips.CMI.CMI;
import com.google.common.base.Preconditions;
import io.github.projectunified.unihologram.api.Hologram;
import io.github.projectunified.unihologram.api.HologramLine;
import io.github.projectunified.unihologram.spigot.line.ItemHologramLine;
import io.github.projectunified.unihologram.spigot.line.SkullHologramLine;
import io.github.projectunified.unihologram.spigot.line.TextHologramLine;
import net.Zrips.CMILib.Container.CMILocation;
import net.Zrips.CMILib.Items.CMIItemStack;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
            com.Zrips.CMI.Modules.Holograms.CMIHologram hologram = new com.Zrips.CMI.Modules.Holograms.CMIHologram(name, new CMILocation(location));
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

    private static String toLine(HologramLine line) {
        if (line instanceof ItemHologramLine) {
            ItemStack item = ((ItemHologramLine) line).getContent();
            return "ICON:" + CMIItemStack.serialize(item);
        } else if (line instanceof SkullHologramLine) {
            return "ICON:head:" + ((SkullHologramLine) line).getContent();
        } else {
            return line.getRawContent();
        }
    }

    private static HologramLine fromLine(String line) {
        if (line.toLowerCase(Locale.ROOT).startsWith("icon:head:")) {
            return new SkullHologramLine(line.substring(10));
        } else if (line.toLowerCase(Locale.ROOT).startsWith("icon:")) {
            CMIItemStack item = CMIItemStack.deserialize(line.substring(5));
            return new ItemHologramLine(item.getItemStack());
        } else {
            return new TextHologramLine(line);
        }
    }

    private static List<String> toLine(List<HologramLine> lines) {
        return lines.stream().map(CMIHologram::toLine).collect(Collectors.toList());
    }

    private static List<HologramLine> fromLine(List<String> lines) {
        return lines.stream().map(CMIHologram::fromLine).collect(Collectors.toList());
    }

    private void checkHologramInitialized() {
        Preconditions.checkNotNull(hologram, "Hologram is not initialized");
    }

    @Override
    public @NotNull List<HologramLine> getLines() {
        checkHologramInitialized();
        return fromLine(hologram.getLines());
    }

    @Override
    public void setLines(@NotNull List<HologramLine> lines) {
        checkHologramInitialized();
        hologram.setLines(toLine(lines));
        hologram.refresh();
    }

    @Override
    public void addLine(@NotNull HologramLine line) {
        checkHologramInitialized();
        hologram.addLine(toLine(line));
        hologram.refresh();
    }

    @Override
    public void setLine(int index, @NotNull HologramLine line) {
        checkHologramInitialized();
        hologram.setLine(index, toLine(line));
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
