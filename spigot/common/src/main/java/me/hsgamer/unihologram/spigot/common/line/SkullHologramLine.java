package me.hsgamer.unihologram.spigot.common.line;

import io.github.projectunified.unihologram.common.line.TextHologramLine;

import java.util.Map;

/**
 * The hologram line with skull
 */
public class SkullHologramLine extends TextHologramLine {
    /**
     * Create a new line
     *
     * @param content  the content
     * @param settings the settings
     */
    public SkullHologramLine(String content, Map<String, Object> settings) {
        super(content, settings);
    }

    /**
     * Create a new line
     *
     * @param content the content
     */
    public SkullHologramLine(String content) {
        super(content);
    }
}
