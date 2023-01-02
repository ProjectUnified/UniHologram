package me.hsgamer.unihologram.spigot.vanilla.hologram;

import me.hsgamer.unihologram.common.api.HologramLine;
import me.hsgamer.unihologram.common.hologram.SimpleHologram;
import me.hsgamer.unihologram.common.line.TextHologramLine;
import me.hsgamer.unihologram.spigot.common.hologram.extra.Colored;
import me.hsgamer.unihologram.spigot.common.line.ItemHologramLine;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple hologram for Vanilla
 */
public class VanillaHologram extends SimpleHologram<Location> implements Colored {
    private static final int VERSION;

    static {
        Matcher matcher = Pattern.compile("MC: \\d\\.(\\d+)").matcher(Bukkit.getVersion());
        if (matcher.find()) {
            VERSION = Integer.parseInt(matcher.group(1));
        } else {
            VERSION = -1;
        }
    }

    private final List<Entity> entities = new ArrayList<>();

    public VanillaHologram(String name, Location location) {
        super(name, location);
    }

    @Override
    protected void updateHologram() {
        clearHologram();

        World world = location.getWorld();
        if (world == null) {
            return;
        }

        Location currentLocation = location.clone();
        for (HologramLine line : lines) {
            currentLocation = currentLocation.clone().add(0, -0.27, 0);
            Entity entity;
            if (line instanceof ItemHologramLine && VERSION >= 10) {
                ItemStack itemStack = ((ItemHologramLine) line).getContent();
                entity = world.dropItem(currentLocation, itemStack);
                entity.setGravity(false);
                entity.setInvulnerable(true);
                Item item = (Item) entity;
                item.setPickupDelay(Integer.MAX_VALUE);
                item.setCustomNameVisible(false);
            } else {
                entity = world.spawn(currentLocation, ArmorStand.class);
                ArmorStand armorStand = (ArmorStand) entity;
                armorStand.setGravity(false);
                armorStand.setVisible(false);
                armorStand.setCustomNameVisible(true);
                armorStand.setInvulnerable(true);
                if (line instanceof TextHologramLine) {
                    armorStand.setCustomName(colorize(((TextHologramLine) line).getContent()));
                } else {
                    armorStand.setCustomName(line.getRawContent());
                }
            }
            entities.add(entity);
        }
    }

    @Override
    protected void initHologram() {
        // EMPTY
    }

    @Override
    protected void clearHologram() {
        entities.forEach(entity -> {
            try {
                entity.remove();
            } catch (Exception ignored) {
                // IGNORED
            }
        });
        entities.clear();
    }
}
