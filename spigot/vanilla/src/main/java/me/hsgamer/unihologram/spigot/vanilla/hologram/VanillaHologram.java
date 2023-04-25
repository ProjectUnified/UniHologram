package me.hsgamer.unihologram.spigot.vanilla.hologram;

import me.hsgamer.unihologram.common.api.HologramLine;
import me.hsgamer.unihologram.common.hologram.SimpleHologram;
import me.hsgamer.unihologram.common.line.TextHologramLine;
import me.hsgamer.unihologram.spigot.common.hologram.extra.Colored;
import me.hsgamer.unihologram.spigot.common.line.ItemHologramLine;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple hologram for Vanilla
 */
public class VanillaHologram extends SimpleHologram<Location> implements Colored {
    private static final int VERSION;
    private static final boolean IS_FOLIA;

    static {
        Matcher matcher = Pattern.compile("MC: \\d\\.(\\d+)").matcher(Bukkit.getVersion());
        if (matcher.find()) {
            VERSION = Integer.parseInt(matcher.group(1));
        } else {
            VERSION = -1;
        }

        boolean isFolia;
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            isFolia = true;
        } catch (ClassNotFoundException ignored) {
            isFolia = false;
        }
        IS_FOLIA = isFolia;
    }

    private final Queue<Entity> entities = new ConcurrentLinkedQueue<>();
    private final AtomicReference<List<HologramLine>> linesRef = new AtomicReference<>();
    private final Plugin plugin;
    private Runnable cancelTaskRunnable;

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

    private void initTask() {
        if (IS_FOLIA) {
            cancelTaskRunnable = Bukkit.getRegionScheduler().runAtFixedRate(plugin, location, s -> updateHologramEntity(), 5, 5)::cancel;
        } else {
            cancelTaskRunnable = Bukkit.getScheduler().runTaskTimer(plugin, this::updateHologramEntity, 5, 5)::cancel;
        }
    }

    @Override
    protected void initHologram() {
        initTask();
    }

    @Override
    public void setLocation(Location location) {
        if (cancelTaskRunnable != null) {
            cancelTaskRunnable.run();
            cancelTaskRunnable = null;
        }
        super.setLocation(location);
        initTask();
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
                    item.setItemStack(((ItemHologramLine) line).getContent());
                    item.teleport(itemLocation);
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
                    armorStand.teleport(currentLocation);
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

        entities.addAll(newEntities);
    }

    @Override
    protected void updateHologram() {
        linesRef.set(Collections.unmodifiableList(lines));
    }

    @Override
    protected void clearHologram() {
        if (cancelTaskRunnable != null) {
            cancelTaskRunnable.run();
            cancelTaskRunnable = null;
        }

        Runnable runnable = () -> {
            while (true) {
                Entity entity = entities.poll();
                if (entity == null) {
                    break;
                }
                removeIfNotNull(entity);
            }
        };
        if (plugin.isEnabled()) {
            if (IS_FOLIA) {
                Bukkit.getRegionScheduler().execute(plugin, location, runnable);
            } else {
                Bukkit.getScheduler().runTask(plugin, runnable);
            }
        } else {
            runnable.run();
        }

        linesRef.set(null);
    }
}
