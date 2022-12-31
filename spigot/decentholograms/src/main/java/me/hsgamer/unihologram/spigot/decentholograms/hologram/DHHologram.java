package me.hsgamer.unihologram.spigot.decentholograms.hologram;

import com.google.common.base.Preconditions;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import eu.decentsoftware.holograms.api.utils.items.HologramItem;
import me.hsgamer.unihologram.common.api.HologramLine;
import me.hsgamer.unihologram.common.hologram.SimpleHologram;
import me.hsgamer.unihologram.common.line.TextHologramLine;
import me.hsgamer.unihologram.spigot.common.line.ItemHologramLine;
import me.hsgamer.unihologram.spigot.common.line.SkullHologramLine;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DHHologram extends SimpleHologram {
    private static final boolean IS_FLAT;

    static {
        boolean isFlat = false;
        try {
            Class.forName("org.bukkit.block.data.BlockData");
            isFlat = true;
        } catch (ClassNotFoundException ignored) {
        }
        IS_FLAT = isFlat;
    }

    private final String name;
    private final Location location;
    private Hologram hologram;

    public DHHologram(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    private void checkHologramInitialized() {
        Preconditions.checkNotNull(hologram, "Hologram is not initialized");
    }

    @Override
    public void setLines(@NotNull List<HologramLine<?>> lines) {
        checkHologramInitialized();
        List<String> content = new ArrayList<>();
        for (HologramLine<?> line : lines) {
            if (line instanceof ItemHologramLine) {
                content.add("#ICON:" + HologramItem.fromItemStack(((ItemHologramLine) line).getContent()).getContent());
            } else if (line instanceof SkullHologramLine) {
                String lineContent = ((SkullHologramLine) line).getContent();
                boolean smallHead = Optional.ofNullable(line.getSettings().get("small-head")).map(String::valueOf).map(Boolean::parseBoolean).orElse(false);
                content.add("#" + (smallHead ? "SMALLHEAD" : "HEAD") + ": " + (IS_FLAT ? "PLAYER_HEAD" : "SKULL_ITEM") + " (" + lineContent + ")");
            } else if (line instanceof TextHologramLine) {
                content.add(((TextHologramLine) line).getContent());
            } else {
                content.add(line.getRawContent());
            }
        }
        DHAPI.setHologramLines(hologram, content);
    }

    @Override
    public void init() {
        hologram = DHAPI.createHologram(name, location);
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
}
