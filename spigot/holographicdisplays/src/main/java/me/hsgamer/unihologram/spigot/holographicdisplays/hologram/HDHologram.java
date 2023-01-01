package me.hsgamer.unihologram.spigot.holographicdisplays.hologram;

import com.google.common.base.Preconditions;
import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import me.filoghost.holographicdisplays.api.hologram.HologramLines;
import me.hsgamer.unihologram.common.api.HologramLine;
import me.hsgamer.unihologram.common.line.EmptyHologramLine;
import me.hsgamer.unihologram.common.line.TextHologramLine;
import me.hsgamer.unihologram.spigot.common.hologram.CommonSpigotHologram;
import me.hsgamer.unihologram.spigot.common.line.ItemHologramLine;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * The hologram for HolographicDisplays
 */
public class HDHologram implements CommonSpigotHologram {
    private final String name;
    private final Supplier<Hologram> hologramSupplier;
    private Hologram hologram;

    /**
     * Create a new hologram
     *
     * @param plugin   the plugin
     * @param name     the name
     * @param location the location
     */
    public HDHologram(Plugin plugin, String name, Location location) {
        this.name = name;
        this.hologramSupplier = () -> HolographicDisplaysAPI.get(plugin).createHologram(location);
    }

    private void checkHologramInitialized() {
        Preconditions.checkNotNull(hologram, "Hologram is not initialized");
    }

    private HologramLine fromHDLine(me.filoghost.holographicdisplays.api.hologram.line.HologramLine hdLine) {
        if (hdLine instanceof me.filoghost.holographicdisplays.api.hologram.line.ItemHologramLine) {
            return new ItemHologramLine(((me.filoghost.holographicdisplays.api.hologram.line.ItemHologramLine) hdLine).getItemStack());
        } else if (hdLine instanceof me.filoghost.holographicdisplays.api.hologram.line.TextHologramLine) {
            return new TextHologramLine(decolorize(((me.filoghost.holographicdisplays.api.hologram.line.TextHologramLine) hdLine).getText()));
        } else {
            return new EmptyHologramLine();
        }
    }

    @Override
    public @NotNull List<HologramLine> getLines() {
        checkHologramInitialized();
        List<HologramLine> lines = new ArrayList<>();
        HologramLines hdLines = hologram.getLines();
        int size = hdLines.size();
        for (int i = 0; i < size; i++) {
            lines.add(fromHDLine(hdLines.get(i)));
        }
        return Collections.unmodifiableList(lines);
    }

    @Override
    public void setLines(@NotNull List<HologramLine> lines) {
        checkHologramInitialized();
        HologramLines hdLines = hologram.getLines();
        hdLines.clear();
        for (HologramLine line : lines) {
            if (line instanceof ItemHologramLine) {
                hdLines.appendItem(((ItemHologramLine) line).getContent());
            } else if (line instanceof TextHologramLine) {
                hdLines.appendText(colorize(((TextHologramLine) line).getContent()));
            } else {
                hdLines.appendText(line.getRawContent());
            }
        }
    }

    @Override
    public void addLine(@NotNull HologramLine line) {
        checkHologramInitialized();
        HologramLines hdLines = hologram.getLines();
        if (line instanceof ItemHologramLine) {
            hdLines.appendItem(((ItemHologramLine) line).getContent());
        } else if (line instanceof TextHologramLine) {
            hdLines.appendText(colorize(((TextHologramLine) line).getContent()));
        } else {
            hdLines.appendText(line.getRawContent());
        }
    }

    @Override
    public void insertLine(int index, @NotNull HologramLine line) {
        checkHologramInitialized();
        HologramLines hdLines = hologram.getLines();
        if (line instanceof ItemHologramLine) {
            hdLines.insertItem(index, ((ItemHologramLine) line).getContent());
        } else if (line instanceof TextHologramLine) {
            hdLines.insertText(index, colorize(((TextHologramLine) line).getContent()));
        } else {
            hdLines.insertText(index, line.getRawContent());
        }
    }

    @Override
    public void removeLine(int index) {
        checkHologramInitialized();
        hologram.getLines().remove(index);
    }

    @Override
    public Optional<HologramLine> getLine(int index) {
        checkHologramInitialized();
        HologramLines hdLines = hologram.getLines();
        if (index < 0 || index >= hdLines.size()) {
            return Optional.empty();
        }
        return Optional.of(fromHDLine(hdLines.get(index)));
    }

    @Override
    public int size() {
        checkHologramInitialized();
        return hologram.getLines().size();
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
        try {
            if (hologram != null) {
                hologram.delete();
                hologram = null;
            }
        } catch (Exception ignored) {
            // IGNORED
        }
    }

    @Override
    public boolean isInitialized() {
        return hologram != null;
    }

    @Override
    public Location getLocation() {
        checkHologramInitialized();
        return hologram.getPosition().toLocation();
    }

    @Override
    public void setLocation(Location location) {
        checkHologramInitialized();
        hologram.setPosition(location);
    }
}
