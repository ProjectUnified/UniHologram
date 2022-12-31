package me.hsgamer.unihologram.spigot.holographicdisplays.hologram;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.line.ItemLine;
import com.gmail.filoghost.holographicdisplays.api.line.TextLine;
import com.google.common.base.Preconditions;
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
import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class HDLegacyHologram implements CommonSpigotHologram {
    private final String name;
    private final Supplier<Hologram> hologramSupplier;
    private Hologram hologram;

    public HDLegacyHologram(Plugin plugin, String name, Location location) {
        this.name = name;
        this.hologramSupplier = () -> HologramsAPI.createHologram(plugin, location);
    }

    private void checkHologramInitialized() {
        Preconditions.checkNotNull(hologram, "Hologram is not initialized");
    }

    @Override
    public @NotNull List<HologramLine<?>> getLines() {
        checkHologramInitialized();
        List<HologramLine<?>> lines = new ArrayList<>();
        int size = hologram.size();
        for (int i = 0; i < size; i++) {
            com.gmail.filoghost.holographicdisplays.api.line.HologramLine hdLine = hologram.getLine(i);
            if (hdLine instanceof ItemLine) {
                lines.add(new ItemHologramLine(((ItemLine) hdLine).getItemStack()));
            } else if (hdLine instanceof TextLine) {
                lines.add(new TextHologramLine(((TextLine) hdLine).getText()));
            } else {
                lines.add(new EmptyHologramLine());
            }
        }
        return Collections.unmodifiableList(lines);
    }

    @Override
    public void setLines(@NotNull List<HologramLine<?>> lines) {
        checkHologramInitialized();
        hologram.clearLines();
        for (HologramLine<?> line : lines) {
            if (line instanceof ItemHologramLine) {
                hologram.appendItemLine(((ItemHologramLine) line).getContent());
            } else if (line instanceof TextHologramLine) {
                hologram.appendTextLine(((TextHologramLine) line).getContent());
            } else {
                hologram.appendTextLine("");
            }
        }
    }

    @Override
    public void addLine(@NotNull HologramLine<?> line) {
        checkHologramInitialized();
        if (line instanceof ItemHologramLine) {
            hologram.appendItemLine(((ItemHologramLine) line).getContent());
        } else if (line instanceof TextHologramLine) {
            hologram.appendTextLine(((TextHologramLine) line).getContent());
        } else {
            hologram.appendTextLine("");
        }
    }

    @Override
    public void setLine(int index, @NotNull HologramLine<?> line) {
        checkHologramInitialized();
        if (line instanceof ItemHologramLine) {
            hologram.insertItemLine(index, ((ItemHologramLine) line).getContent());
        } else if (line instanceof TextHologramLine) {
            hologram.insertTextLine(index, ((TextHologramLine) line).getContent());
        } else {
            hologram.insertTextLine(index, "");
        }
        hologram.removeLine(index + 1);
    }

    @Override
    public void removeLine(int index) {
        checkHologramInitialized();
        hologram.removeLine(index);
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
    public Location getLocation() {
        checkHologramInitialized();
        return hologram.getLocation();
    }

    @Override
    public void setLocation(Location location) {
        checkHologramInitialized();
        hologram.teleport(location);
    }
}
