package me.hsgamer.unihologram.spigot.vanilla.hologram;

import me.hsgamer.unihologram.common.api.HologramLine;
import io.github.projectunified.unihologram.common.hologram.SimpleHologram;
import io.github.projectunified.unihologram.common.line.TextHologramLine;
import me.hsgamer.unihologram.spigot.common.hologram.extra.Colored;
import me.hsgamer.unihologram.spigot.common.line.ItemHologramLine;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;
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

    private final Queue<Entity> entities = new LinkedBlockingQueue<>();
    private final AtomicReference<List<Entity>> newEntitiesRef = new AtomicReference<>();
    private final AtomicReference<List<HologramLine>> linesRef = new AtomicReference<>();
    private final Plugin plugin;
    private BukkitTask updateTask;

    /**
     * Create a new hologram
     *
     * @param plugin   the plugin
     * @param name     the name of the hologram
     * @param location the location of the hologram
     */
    public VanillaHologram(Plugin plugin, String name, Location location) {
        super(name, location);
        this.plugin = plugin;
    }

    private static void removeIfNotNull(Entity entity) {
        if (entity != null) {
            try {
                entity.remove();
            } catch (Exception ignored) {
                // IGNORED
            }
        }
    }

    private static void teleport(Entity entity, Location location) {
        Location currentLocation = entity.getLocation();
        if (currentLocation.getX() == location.getX() && currentLocation.getY() == location.getY() && currentLocation.getZ() == location.getZ()) {
            return;
        }
        entity.teleport(location);
    }

    @Override
    protected void initHologram() {
        if (updateTask != null) {
            updateTask.cancel();
            updateTask = null;
        }
        updateTask = Bukkit.getScheduler().runTaskTimer(plugin, this::updateHologramEntity, 5, 5);
    }

    private void clearEntityQueue() {
        while (true) {
            Entity entity = entities.poll();
            if (entity == null) {
                break;
            }
            removeIfNotNull(entity);
        }
    }

    private void updateHologramEntity() {
        List<HologramLine> toUpdate = linesRef.getAndSet(null);
        if (toUpdate == null) {
            return;
        }

        World world = location.getWorld();
        if (world == null) {
            return;
        }

        List<Entity> newEntities = new ArrayList<>();
        newEntitiesRef.set(newEntities);

        Location currentLocation = location.clone().add(0, -2, 0);
        for (HologramLine line : toUpdate) {
            Entity entity = entities.poll();

            currentLocation = currentLocation.clone().add(0, -0.27, 0);

            if (line instanceof ItemHologramLine && VERSION >= 10) {
                currentLocation = currentLocation.clone().add(0, -0.4, 0);
                Location itemLocation = currentLocation.clone().add(0, 2.2, 0);

                Item item;
                if (entity instanceof Item) {
                    item = (Item) entity;
                    ItemStack current = item.getItemStack();
                    ItemStack expected = ((ItemHologramLine) line).getContent();
                    if (!expected.isSimilar(current)) {
                        item.setItemStack(expected);
                    }
                    teleport(item, itemLocation);
                } else {
                    removeIfNotNull(entity);
                    item = world.dropItem(itemLocation, ((ItemHologramLine) line).getContent());
                    item.setGravity(false);
                    item.setVelocity(new Vector(0, 0, 0));
                    item.setInvulnerable(true);
                    item.setPickupDelay(Integer.MAX_VALUE);
                    item.setCustomNameVisible(false);
                }
                entity = item;
            } else {
                ArmorStand armorStand;
                if (entity instanceof ArmorStand) {
                    armorStand = (ArmorStand) entity;
                    teleport(armorStand, currentLocation);
                } else {
                    removeIfNotNull(entity);
                    armorStand = world.spawn(currentLocation, ArmorStand.class);
                    armorStand.setGravity(false);
                    armorStand.setVisible(false);
                    armorStand.setCustomNameVisible(true);
                    armorStand.setInvulnerable(true);
                }
                entity = armorStand;

                String content = line instanceof TextHologramLine
                        ? colorize(((TextHologramLine) line).getContent())
                        : line.getRawContent();
                armorStand.setCustomName(content.isEmpty() ? ChatColor.RESET.toString() : content);
            }

            newEntities.add(entity);
        }

        clearEntityQueue();

        entities.addAll(newEntities);
        newEntitiesRef.set(null);
    }

    @Override
    protected void updateHologram() {
        linesRef.set(Collections.unmodifiableList(lines));
    }

    @Override
    protected void clearHologram() {
        if (updateTask != null) {
            updateTask.cancel();
            updateTask = null;
        }

        Runnable runnable = () -> {
            clearEntityQueue();

            List<Entity> newEntities = newEntitiesRef.getAndSet(null);
            if (newEntities != null) {
                for (Entity entity : newEntities) {
                    removeIfNotNull(entity);
                }
            }
        };
        if (Bukkit.isPrimaryThread()) {
            runnable.run();
        } else {
            Bukkit.getScheduler().runTask(plugin, runnable);
        }

        linesRef.set(null);
    }
}
