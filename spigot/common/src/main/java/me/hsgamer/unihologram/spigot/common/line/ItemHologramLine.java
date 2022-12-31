package me.hsgamer.unihologram.spigot.common.line;

import me.hsgamer.unihologram.common.line.AbstractHologramLine;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ItemHologramLine extends AbstractHologramLine<ItemStack> {
    public ItemHologramLine(ItemStack content, Map<String, Object> settings) {
        super(content, settings);
    }

    public ItemHologramLine(ItemStack content) {
        super(content);
    }
}
