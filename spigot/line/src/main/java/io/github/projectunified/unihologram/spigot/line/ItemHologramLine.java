package io.github.projectunified.unihologram.spigot.line;

import org.bukkit.inventory.ItemStack;

import java.util.Map;

/**
 * The hologram line with item
 */
public class ItemHologramLine extends AbstractHologramLine<ItemStack> {
    /**
     * Create a new line
     *
     * @param content  the content
     * @param settings the settings
     */
    public ItemHologramLine(ItemStack content, Map<String, Object> settings) {
        super(content, settings);
    }

    /**
     * Create a new line
     *
     * @param content the content
     */
    public ItemHologramLine(ItemStack content) {
        super(content);
    }
}
