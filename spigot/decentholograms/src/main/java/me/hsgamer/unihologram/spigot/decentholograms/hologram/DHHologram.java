package me.hsgamer.unihologram.spigot.decentholograms.hologram;

import com.google.common.base.Preconditions;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import eu.decentsoftware.holograms.api.holograms.HologramPage;
import eu.decentsoftware.holograms.api.utils.items.HologramItem;
import me.hsgamer.unihologram.common.api.HologramLine;
import me.hsgamer.unihologram.common.line.TextHologramLine;
import me.hsgamer.unihologram.spigot.common.hologram.CommonSpigotHologram;
import me.hsgamer.unihologram.spigot.common.line.ItemHologramLine;
import me.hsgamer.unihologram.spigot.common.line.SkullHologramLine;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * The hologram for DecentHolograms
 */
public class DHHologram implements CommonSpigotHologram {
    private static final boolean IS_FLAT;

    static {
        boolean isFlat = false;
        try {
            Class.forName("org.bukkit.block.data.BlockData");
            isFlat = true;
        } catch (ClassNotFoundException ignored) {
            // IGNORED
        }
        IS_FLAT = isFlat;
    }

    private final Supplier<Hologram> hologramSupplier;
    private final String name;
    private Hologram hologram;

    /**
     * Create a new hologram
     *
     * @param name     the name of the hologram
     * @param location the location of the hologram
     */
    public DHHologram(String name, Location location) {
        this.name = name;
        hologramSupplier = () -> DHAPI.createHologram(name, location);
    }

    /**
     * Create a new hologram
     *
     * @param hologram the hologram
     */
    public DHHologram(Hologram hologram) {
        this(hologram.getName(), hologram.getLocation().clone());
        this.hologram = hologram;
    }

    private void checkHologramInitialized() {
        Preconditions.checkNotNull(hologram, "Hologram is not initialized");
    }

    private String toDHContent(HologramLine line) {
        if (line instanceof ItemHologramLine) {
            return "#ICON:" + HologramItem.fromItemStack(((ItemHologramLine) line).getContent()).getContent();
        } else if (line instanceof SkullHologramLine) {
            String lineContent = ((SkullHologramLine) line).getContent();
            boolean smallHead = Optional.ofNullable(line.getSettings().get("small-head")).map(String::valueOf).map(Boolean::parseBoolean).orElse(false);
            return "#" + (smallHead ? "SMALLHEAD" : "HEAD") + ": " + (IS_FLAT ? "PLAYER_HEAD" : "SKULL_ITEM") + " (" + lineContent + ")";
        } else if (line instanceof TextHologramLine) {
            return ((TextHologramLine) line).getContent();
        } else {
            return line.getRawContent();
        }
    }

    private HologramLine fromDHLine(eu.decentsoftware.holograms.api.holograms.HologramLine line) {
        switch (line.getType()) {
            case ICON:
                return new ItemHologramLine(line.getItem().parse());
            case SMALLHEAD:
            case HEAD:
                return new SkullHologramLine(line.getItem().getExtras());
            default:
                return new TextHologramLine(line.getContent());
        }
    }

    @Override
    public @NotNull List<HologramLine> getLines() {
        checkHologramInitialized();
        HologramPage page = hologram.getPage(0);
        if (page == null) {
            return Collections.emptyList();
        }
        return page.getLines().stream().map(this::fromDHLine).collect(Collectors.toList());
    }

    @Override
    public void setLines(@NotNull List<HologramLine> lines) {
        checkHologramInitialized();
        List<String> content = lines.stream().map(this::toDHContent).collect(Collectors.toList());
        DHAPI.setHologramLines(hologram, content);
    }

    @Override
    public void addLine(@NotNull HologramLine line) {
        checkHologramInitialized();
        DHAPI.addHologramLine(hologram, toDHContent(line));
    }

    @Override
    public void insertLine(int index, @NotNull HologramLine line) {
        checkHologramInitialized();
        DHAPI.insertHologramLine(hologram, index, toDHContent(line));
    }

    @Override
    public void setLine(int index, @NotNull HologramLine line) {
        checkHologramInitialized();
        DHAPI.setHologramLine(hologram, index, toDHContent(line));
    }

    @Override
    public void removeLine(int index) {
        checkHologramInitialized();
        DHAPI.removeHologramLine(hologram, index);
    }

    @Override
    public Optional<HologramLine> getLine(int index) {
        checkHologramInitialized();
        HologramPage page = hologram.getPage(0);
        if (page == null) {
            return Optional.empty();
        }
        eu.decentsoftware.holograms.api.holograms.HologramLine line = page.getLine(index);
        if (line == null) {
            return Optional.empty();
        }
        return Optional.of(fromDHLine(line));
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
    public Location getLocation() {
        checkHologramInitialized();
        return hologram.getLocation();
    }

    @Override
    public void setLocation(Location location) {
        checkHologramInitialized();
        DHAPI.moveHologram(hologram, location);
    }
}
