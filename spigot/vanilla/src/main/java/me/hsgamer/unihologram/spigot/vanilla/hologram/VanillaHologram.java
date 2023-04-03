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

    private final List<Entity> entities = new ArrayList<>();
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

    private void updateHologramEntity() {
        List<HologramLine> toUpdate = linesRef.getAndSet(null);
        if (toUpdate == null) {
            return;
        }

        clearHologramEntity();

        World world = location.getWorld();
        if (world == null) {
            return;
        }

        Location currentLocation = location.clone().add(0, -2, 0);
        for (HologramLine line : toUpdate) {
            currentLocation = currentLocation.clone().add(0, -0.27, 0);
            Entity entity;
            if (line instanceof ItemHologramLine && VERSION >= 10) {
                currentLocation = currentLocation.clone().add(0, -0.4, 0);
                Location itemLocation = currentLocation.clone().add(0, 2.2, 0);
                Item item = world.dropItem(itemLocation, ((ItemHologramLine) line).getContent());
                entity = item;
                entity.setGravity(false);
                entity.setVelocity(new Vector(0, 0, 0));
                entity.teleport(itemLocation);
                entity.setInvulnerable(true);
                item.setPickupDelay(Integer.MAX_VALUE);
                item.setCustomNameVisible(false);
            } else {
                ArmorStand armorStand = world.spawn(currentLocation, ArmorStand.class);
                entity = armorStand;
                armorStand.setGravity(false);
                armorStand.setVisible(false);
                armorStand.setCustomNameVisible(true);
                armorStand.setInvulnerable(true);

                String content = line instanceof TextHologramLine
                        ? colorize(((TextHologramLine) line).getContent())
                        : line.getRawContent();
                armorStand.setCustomName(content.isEmpty() ? ChatColor.RESET.toString() : content);
            }
            entities.add(entity);
        }
    }

    private void clearHologramEntity() {
        entities.forEach(entity -> {
            try {
                entity.remove();
            } catch (Exception ignored) {
                // IGNORED
            }
        });
        entities.clear();
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

        Runnable runnable = this::clearHologramEntity;
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
