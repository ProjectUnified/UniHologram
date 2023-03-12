package me.hsgamer.unihologram.spigot.plugin;

public final class PluginBuild {
    public static final String NAME = "UniHologram";
    public static final String VERSION = "${project.version}";
    public static final String DESCRIPTION = "${project.description}";
    public static final String AUTHOR = "HSGamer";

    private PluginBuild() {
        throw new IllegalStateException("Utility class");
    }
}
