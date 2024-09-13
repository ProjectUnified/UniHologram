package io.github.projectunified.unihologram.spigot.line;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * A hologram line with text
 */
public class TextHologramLine extends AbstractHologramLine<String> {
    /**
     * Create a new line
     *
     * @param content  the content
     * @param settings the settings
     */
    public TextHologramLine(String content, Map<String, Object> settings) {
        super(content, settings);
    }

    /**
     * Create a new line
     *
     * @param content the content
     */
    public TextHologramLine(String content) {
        super(content);
    }

    /**
     * Create a new line from the colored content
     *
     * @param content the colored content
     * @return the new line
     */
    public static TextHologramLine fromColoredContent(@NotNull String content) {
        return new TextHologramLine(content.replace(ChatColor.COLOR_CHAR, '&'));
    }

    /**
     * Get the colored content
     *
     * @return the colored content
     */
    public String getColoredContent() {
        return ChatColor.translateAlternateColorCodes('&', getContent());
    }
}
