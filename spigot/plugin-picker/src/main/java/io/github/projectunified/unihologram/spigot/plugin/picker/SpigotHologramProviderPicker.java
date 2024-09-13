package io.github.projectunified.unihologram.spigot.plugin.picker;

import io.github.projectunified.unihologram.picker.HologramProviderPicker;
import io.github.projectunified.unihologram.spigot.plugin.cmi.CMIHologramProvider;
import io.github.projectunified.unihologram.spigot.plugin.decentholograms.DHHologramProvider;
import io.github.projectunified.unihologram.spigot.plugin.fancyholograms.FHHologramProvider;
import io.github.projectunified.unihologram.spigot.plugin.folia.FoliaHologramProvider;
import io.github.projectunified.unihologram.spigot.plugin.holographicdisplays.HDHologramProvider;
import io.github.projectunified.unihologram.spigot.plugin.vanilla.VanillaHologramProvider;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

public class SpigotHologramProviderPicker extends HologramProviderPicker<Plugin, Location> {
    /**
     * Create a new picker
     *
     * @param input the input
     */
    public SpigotHologramProviderPicker(Plugin input) {
        super(input);
        add(CMIHologramProvider::isAvailable, CMIHologramProvider::new);
        add(DHHologramProvider::isAvailable, DHHologramProvider::new);
        add(FHHologramProvider::isAvailable, FHHologramProvider::new);
        add(HDHologramProvider::isAvailable, HDHologramProvider::new);
        add(FoliaHologramProvider::isAvailable, FoliaHologramProvider::new);
        add(() -> true, VanillaHologramProvider::new);
    }
}
