package me.hsgamer.unihologram.spigot.folia.hologram;

import me.hsgamer.unihologram.common.api.HologramLine;
import me.hsgamer.unihologram.common.hologram.SimpleHologram;
import me.hsgamer.unihologram.common.line.TextHologramLine;
import me.hsgamer.unihologram.spigot.common.hologram.extra.Colored;
import me.hsgamer.unihologram.spigot.common.line.ItemHologramLine;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A simple hologram for Folia
 */
public class FoliaHologram extends SimpleHologram<Location> implements Colored {
    private final Plugin plugin;
    private final AtomicReference<List<Entity>> entityRef = new AtomicReference<>();
    private final Object lock = new Object();

    /**
     * Create a new hologram
     *
     * @param plugin   the plugin
     * @param name     the name of the hologram
     * @param location the location of the hologram
     */
    public FoliaHologram(Plugin plugin, String name, Location location) {
        super(name, location);
        this.plugin = plugin;
    }

    private void despawnEntity() {
        List<Entity> entities = entityRef.get();
        if (entities != null) {
            entities.forEach(Entity::remove);
            entityRef.set(null);
        }
    }

    @Override
    protected void updateHologram() {
        Bukkit.getRegionScheduler().execute(plugin, location, () -> {
            synchronized (lock) {
                despawnEntity();

                List<Entity> newEntities = new ArrayList<>();
                for (HologramLine line : getLines()) {
                    Entity entity;
                    if (line instanceof ItemHologramLine) {
                        Item item = location.getWorld().dropItem(location, ((ItemHologramLine) line).getContent());
                        entity = item;
                        item.setGravity(false);
                        item.setVelocity(new Vector(0, 0, 0));
                        item.setInvulnerable(true);
                        item.setPickupDelay(Integer.MAX_VALUE);
                        item.setCustomNameVisible(false);
                    } else {
                        ArmorStand armorStand = location.getWorld().spawn(location, ArmorStand.class);
                        entity = armorStand;
                        armorStand.setGravity(false);
                        armorStand.setInvulnerable(true);
                        armorStand.setCustomNameVisible(true);
                        String content = line instanceof TextHologramLine
                                ? colorize(((TextHologramLine) line).getContent())
                                : line.getRawContent();
                        armorStand.customName(content.isEmpty()
                                ? Component.empty()
                                : LegacyComponentSerializer.legacyAmpersand().deserialize(content)
                        );
                    }
                    newEntities.add(entity);
                }
                entityRef.set(newEntities);
            }
        });
    }

    @Override
    protected void initHologram() {
        // EMPTY
    }

    @Override
    protected void clearHologram() {
        Runnable runnable = () -> {
            synchronized (lock) {
                despawnEntity();
            }
        };
        if (Bukkit.isOwnedByCurrentRegion(location)) {
            runnable.run();
        } else {
            Bukkit.getRegionScheduler().execute(plugin, location, runnable);
        }
    }
}
