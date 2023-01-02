package me.hsgamer.unihologram.spigot.holographicdisplays.hologram;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.line.ItemLine;
import com.gmail.filoghost.holographicdisplays.api.line.TextLine;
import com.google.common.base.Preconditions;
import me.hsgamer.unihologram.common.api.HologramLine;
import me.hsgamer.unihologram.common.line.EmptyHologramLine;
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
import java.util.function.Supplier;

/**
 * The legacy hologram for HolographicDisplays
 */
@SuppressWarnings("deprecation")
public class HDLegacyHologram implements me.hsgamer.unihologram.common.api.Hologram<Location>, PlayerVisibility, Colored {
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
    public HDLegacyHologram(Plugin plugin, String name, Location location) {
        this.name = name;
        this.hologramSupplier = () -> HologramsAPI.createHologram(plugin, location);
    }

    private void checkHologramInitialized() {
        Preconditions.checkNotNull(hologram, "Hologram is not initialized");
    }

    private HologramLine fromHDLine(com.gmail.filoghost.holographicdisplays.api.line.HologramLine hdLine) {
        if (hdLine instanceof ItemLine) {
            return new ItemHologramLine(((ItemLine) hdLine).getItemStack());
        } else if (hdLine instanceof TextLine) {
            return new TextHologramLine(decolorize(((TextLine) hdLine).getText()));
        } else {
            return new EmptyHologramLine();
        }
    }

    @Override
    public @NotNull List<HologramLine> getLines() {
        checkHologramInitialized();
        List<HologramLine> lines = new ArrayList<>();
        int size = hologram.size();
        for (int i = 0; i < size; i++) {
            lines.add(fromHDLine(hologram.getLine(i)));
        }
        return Collections.unmodifiableList(lines);
    }

    @Override
    public void setLines(@NotNull List<HologramLine> lines) {
        checkHologramInitialized();
        hologram.clearLines();
        for (HologramLine line : lines) {
            if (line instanceof ItemHologramLine) {
                hologram.appendItemLine(((ItemHologramLine) line).getContent());
            } else if (line instanceof TextHologramLine) {
                hologram.appendTextLine(colorize(((TextHologramLine) line).getContent()));
            } else {
                hologram.appendTextLine(line.getRawContent());
            }
        }
    }

    @Override
    public void addLine(@NotNull HologramLine line) {
        checkHologramInitialized();
        if (line instanceof ItemHologramLine) {
            hologram.appendItemLine(((ItemHologramLine) line).getContent());
        } else if (line instanceof TextHologramLine) {
            hologram.appendTextLine(colorize(((TextHologramLine) line).getContent()));
        } else {
            hologram.appendTextLine(line.getRawContent());
        }
    }

    @Override
    public void insertLine(int index, @NotNull HologramLine line) {
        checkHologramInitialized();
        if (line instanceof ItemHologramLine) {
            hologram.insertItemLine(index, ((ItemHologramLine) line).getContent());
        } else if (line instanceof TextHologramLine) {
            hologram.insertTextLine(index, colorize(((TextHologramLine) line).getContent()));
        } else {
            hologram.insertTextLine(index, line.getRawContent());
        }
    }

    @Override
    public void removeLine(int index) {
        checkHologramInitialized();
        hologram.removeLine(index);
    }

    @Override
    public Optional<HologramLine> getLine(int index) {
        checkHologramInitialized();
        if (index < 0 || index >= hologram.size()) {
            return Optional.empty();
        }
        return Optional.of(fromHDLine(hologram.getLine(index)));
    }

    @Override
    public int size() {
        checkHologramInitialized();
        return hologram.size();
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
        return hologram.getLocation();
    }

    @Override
    public void setLocation(Location location) {
        checkHologramInitialized();
        hologram.teleport(location);
    }

    @Override
    public boolean isVisible(Player viewer) {
        checkHologramInitialized();
        return hologram.getVisibilityManager().isVisibleTo(viewer);
    }

    @Override
    public void showAll() {
        checkHologramInitialized();
        hologram.getVisibilityManager().setVisibleByDefault(true);
    }

    @Override
    public void hideAll() {
        checkHologramInitialized();
        hologram.getVisibilityManager().setVisibleByDefault(false);
    }

    @Override
    public void showTo(Player viewer) {
        checkHologramInitialized();
        hologram.getVisibilityManager().showTo(viewer);
    }

    @Override
    public void hideTo(Player viewer) {
        checkHologramInitialized();
        hologram.getVisibilityManager().hideTo(viewer);
    }
}
