package me.hsgamer.unihologram.spigot.cmi.hologram;

import com.Zrips.CMI.CMI;
import me.hsgamer.unihologram.common.api.HologramLine;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

public class CMIHologram implements me.hsgamer.unihologram.common.api.Hologram<Location> {
    private final Supplier<com.Zrips.CMI.Modules.Holograms.CMIHologram> hologramSupplier;
    private final String name;
    private com.Zrips.CMI.Modules.Holograms.CMIHologram hologram;

    public CMIHologram(String name, Location location) {
        this.name = name;
        this.hologramSupplier = () -> {
            com.Zrips.CMI.Modules.Holograms.CMIHologram hologram = new com.Zrips.CMI.Modules.Holograms.CMIHologram(name, location);
            CMI.getInstance().getHologramManager().addHologram(hologram);
            return hologram;
        };
    }

    public CMIHologram(com.Zrips.CMI.Modules.Holograms.CMIHologram hologram) {
        this(hologram.getName(), hologram.getCenterLocation());
        this.hologram = hologram;
    }

    @Override
    public @NotNull List<HologramLine> getLines() {
        return null;
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
    public String getName() {
        return null;
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
}
