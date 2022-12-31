package me.hsgamer.unihologram.common.line;

import me.hsgamer.unihologram.common.api.HologramLine;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;

public class EmptyHologramLine implements HologramLine {
    @Override
    public @NotNull String getContent() {
        return "";
    }

    @Override
    public @NotNull Map<String, Object> getSettings() {
        return Collections.emptyMap();
    }
}
