package me.hsgamer.unihologram.spigot.common.provider;

import me.hsgamer.unihologram.common.api.Hologram;
import me.hsgamer.unihologram.common.api.HologramProvider;
import me.hsgamer.unihologram.common.hologram.NoneHologram;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * A hologram provider for Spigot.
 * It will use the best provider available.
 */
public class SpigotHologramProvider implements HologramProvider<Location> {
    private final Plugin plugin;
    private final HologramProvider<Location> provider;

    /**
     * Create a new hologram provider
     *
     * @param plugin the plugin
     */
    public SpigotHologramProvider(Plugin plugin) {
        this.plugin = plugin;
        this.provider = getProviderOrNone(
                "me.hsgamer.unihologram.spigot.decentholograms.provider.DHHologramProvider",
                "me.hsgamer.unihologram.spigot.holographicdisplays.provider.HDHologramProvider",
                "me.hsgamer.unihologram.spigot.cmi.provider.CMIHologramProvider",
                "me.hsgamer.unihologram.spigot.vanilla.provider.VanillaHologramProvider"
        );
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
            if (method.getReturnType().equals(boolean.class) || method.getReturnType().equals(Boolean.class)) {
                return false;
            }
            return (boolean) method.invoke(null);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            return true;
        }
    }

    private Optional<HologramProvider<Location>> getProvider(String className) {
        Class<?> clazz;
        try {
            clazz = Class.forName(className);
            if (!HologramProvider.class.isAssignableFrom(clazz)) {
                return Optional.empty();
            }
        } catch (ClassNotFoundException e) {
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

    private HologramProvider<Location> getProviderOrNone(String... classNames) {
        for (String className : classNames) {
            Optional<HologramProvider<Location>> optional = getProvider(className);
            if (optional.isPresent()) {
                return optional.get();
            }
        }
        return new HologramProvider<Location>() {
            @Override
            public @NotNull Hologram<Location> createHologram(@NotNull String name, @NotNull Location location) {
                return new NoneHologram<>(name, location);
            }

            @Override
            public Optional<Hologram<Location>> getHologram(@NotNull String name) {
                return Optional.empty();
            }

            @Override
            public Collection<Hologram<Location>> getAllHolograms() {
                return Collections.emptyList();
            }

            @Override
            public boolean isLocal() {
                return true;
            }
        };
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
