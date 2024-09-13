package io.github.projectunified.unihologram.spigot.line;

import io.github.projectunified.unihologram.api.HologramLine;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;

/**
 * An empty line
 */
public class EmptyHologramLine implements HologramLine {
    @Override
    public @NotNull Object getContent() {
        return "";
    }

    @Override
    public @NotNull Map<String, Object> getSettings() {
        return Collections.emptyMap();
    }
}
