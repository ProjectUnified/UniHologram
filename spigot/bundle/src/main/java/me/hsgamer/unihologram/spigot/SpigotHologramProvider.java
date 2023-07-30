package me.hsgamer.unihologram.spigot;

import me.hsgamer.unihologram.common.api.Hologram;
import me.hsgamer.unihologram.common.api.HologramProvider;
import me.hsgamer.unihologram.common.hologram.NoneHologram;
import me.hsgamer.unihologram.common.provider.LocalHologramProvider;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Optional;

/**
 * A hologram provider for Spigot.
 * It will use the best provider available.
 */
public class SpigotHologramProvider implements HologramProvider<Location> {
    private final HologramProvider<Location> provider;

    /**
     * Create a new hologram provider
     *
     * @param plugin the plugin
     */
    public SpigotHologramProvider(Plugin plugin) {
        this.provider = getDefaultProviderOrNone(plugin);
    }

    /**
     * Create a new hologram provider
     */
    public SpigotHologramProvider() {
        this(JavaPlugin.getProvidingPlugin(SpigotHologramProvider.class));
    }

    private static boolean checkClassDependAvailable(Class<?> clazz) {
        try {
            Method method = clazz.getDeclaredMethod("isAvailable");
            if (!method.getReturnType().equals(boolean.class) && !method.getReturnType().equals(Boolean.class)) {
                return false;
            }
            return (boolean) method.invoke(null);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            return true;
        }
    }

    private static Optional<HologramProvider<Location>> getProvider(Plugin plugin, String className) {
        Class<?> clazz;
        try {
            clazz = Class.forName(className);
            if (!HologramProvider.class.isAssignableFrom(clazz)) {
                return Optional.empty();
            }
        } catch (ClassNotFoundException | NoClassDefFoundError e) {
            return Optional.empty();
        }

        if (!checkClassDependAvailable(clazz)) {
            return Optional.empty();
        }

        Constructor<?> constructor;
        boolean hasPluginConstructor;
        try {
            constructor = clazz.getConstructor(Plugin.class);
            hasPluginConstructor = true;
        } catch (NoSuchMethodException e) {
            try {
                constructor = clazz.getConstructor();
                hasPluginConstructor = false;
            } catch (NoSuchMethodException ex) {
                return Optional.empty();
            }
        }

        HologramProvider<Location> provider;
        try {
            if (hasPluginConstructor) {
                //noinspection unchecked
                provider = (HologramProvider<Location>) constructor.newInstance(plugin);
            } else {
                //noinspection unchecked
                provider = (HologramProvider<Location>) constructor.newInstance();
            }
        } catch (ReflectiveOperationException e) {
            return Optional.empty();
        }

        return Optional.of(provider);
    }

    private static HologramProvider<Location> getDefaultProviderOrNone(Plugin plugin) {
        final String[] classNames = {
                "me.hsgamer.unihologram.spigot.decentholograms.provider.DHHologramProvider",
                "me.hsgamer.unihologram.spigot.holographicdisplays.provider.HDHologramProvider",
                "me.hsgamer.unihologram.spigot.fancyholograms.provider.FHHologramProvider",
                "me.hsgamer.unihologram.spigot.cmi.provider.CMIHologramProvider",
                "me.hsgamer.unihologram.spigot.folia.provider.FoliaHologramProvider",
                "me.hsgamer.unihologram.spigot.vanilla.provider.VanillaHologramProvider"
        };

        for (String className : classNames) {
            Optional<HologramProvider<Location>> optional = getProvider(plugin, className);
            if (optional.isPresent()) {
                return optional.get();
            }
        }

        return new LocalHologramProvider<Location>() {
            @Override
            protected @NotNull Hologram<Location> newHologram(@NotNull String name, @NotNull Location location) {
                return new NoneHologram<>(name, location);
            }
        };
    }

    /**
     * Get the original provider
     *
     * @return the original provider
     */
    public @NotNull HologramProvider<Location> getProvider() {
        return provider;
    }

    @Override
    public @NotNull Hologram<Location> createHologram(@NotNull String name, @NotNull Location location) {
        return provider.createHologram(name, location);
    }

    @Override
    public Optional<Hologram<Location>> getHologram(@NotNull String name) {
        return provider.getHologram(name);
    }

    @Override
    public Collection<Hologram<Location>> getAllHolograms() {
        return provider.getAllHolograms();
    }

    @Override
    public boolean isLocal() {
        return provider.isLocal();
    }
}
