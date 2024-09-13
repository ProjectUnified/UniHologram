package io.github.projectunified.unihologram.spigot.decentholograms;

import com.google.common.base.Preconditions;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import eu.decentsoftware.holograms.api.holograms.HologramPage;
import eu.decentsoftware.holograms.api.utils.items.HologramItem;
import io.github.projectunified.unihologram.api.HologramLine;
import io.github.projectunified.unihologram.api.paged.PagedHologram;
import io.github.projectunified.unihologram.spigot.api.page.PlayerPage;
import io.github.projectunified.unihologram.spigot.api.visibility.PlayerVisibility;
import io.github.projectunified.unihologram.spigot.line.ItemHologramLine;
import io.github.projectunified.unihologram.spigot.line.SkullHologramLine;
import io.github.projectunified.unihologram.spigot.line.TextHologramLine;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * The hologram for DecentHolograms
 */
public class DHHologram implements PagedHologram<Location>, PlayerPage, PlayerVisibility {
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
                return new ItemHologramLine(line.getItem().parse(null));
            case SMALLHEAD:
            case HEAD:
                return new SkullHologramLine(line.getItem().getExtras());
            default:
                return new TextHologramLine(line.getContent());
        }
    }

    @Override
    public int getPages() {
        checkHologramInitialized();
        return hologram.getPages().size();
    }

    @Override
    public @NotNull List<HologramLine> getLines(int page) {
        checkHologramInitialized();
        HologramPage hologramPage = hologram.getPage(page);
        if (hologramPage == null) {
            return Collections.emptyList();
        }
        return hologramPage.getLines().stream().map(this::fromDHLine).collect(Collectors.toList());
    }

    @Override
    public void setLines(int page, @NotNull List<HologramLine> lines) {
        checkHologramInitialized();
        List<String> content = lines.stream().map(this::toDHContent).collect(Collectors.toList());
        DHAPI.setHologramLines(hologram, page, content);
    }

    @Override
    public void addLine(int page, @NotNull HologramLine line) {
        checkHologramInitialized();
        DHAPI.addHologramLine(hologram, page, toDHContent(line));
    }

    @Override
    public void insertLine(int page, int index, @NotNull HologramLine line) {
        checkHologramInitialized();
        DHAPI.insertHologramLine(hologram, page, index, toDHContent(line));
    }

    @Override
    public void setLine(int page, int index, @NotNull HologramLine line) {
        checkHologramInitialized();
        DHAPI.setHologramLine(hologram, page, index, toDHContent(line));
    }

    @Override
    public void removeLine(int page, int index) {
        checkHologramInitialized();
        DHAPI.removeHologramLine(hologram, page, index);
    }

    @Override
    public Optional<HologramLine> getLine(int page, int index) {
        checkHologramInitialized();
        HologramPage hologramPage = hologram.getPage(page);
        if (hologramPage == null) {
            return Optional.empty();
        }
        eu.decentsoftware.holograms.api.holograms.HologramLine line = hologramPage.getLine(index);
        if (line == null) {
            return Optional.empty();
        }
        return Optional.of(fromDHLine(line));
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
        DHAPI.moveHologram(hologram, location);
    }

    @Override
    public boolean isVisible(Player viewer) {
        checkHologramInitialized();
        return hologram.isVisible(viewer);
    }

    @Override
    public void showAll() {
        checkHologramInitialized();
        hologram.showAll();
    }

    @Override
    public void hideAll() {
        checkHologramInitialized();
        hologram.hideAll();
    }

    @Override
    public void showTo(Player viewer) {
        checkHologramInitialized();
        hologram.show(viewer, getFirstPage());
    }

    @Override
    public void hideTo(Player viewer) {
        checkHologramInitialized();
        hologram.hide(viewer);
    }

    @Override
    public void setPage(Player viewer, int page) {
        checkHologramInitialized();
        int pages = hologram.getPages().size();
        int actualPage = page;
        if (page < 0) {
            actualPage = pages - 1;
        } else if (page >= pages) {
            actualPage = 0;
        }
        hologram.show(viewer, actualPage);
    }

    @Override
    public int getPage(Player viewer) {
        checkHologramInitialized();
        return hologram.getPlayerPage(viewer);
    }
}
