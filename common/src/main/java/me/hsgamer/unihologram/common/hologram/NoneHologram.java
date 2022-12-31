package me.hsgamer.unihologram.common.hologram;

import me.hsgamer.unihologram.common.api.HologramLine;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NoneHologram extends SimpleHologram {
    public NoneHologram(String name) {
        super(name);
    }

    @Override
    public void setLines(@NotNull List<HologramLine<?>> lines) {
        // EMPTY
    }

    @Override
    public void init() {
        // EMPTY
    }

    @Override
    public void clear() {
        // EMPTY
    }
}
