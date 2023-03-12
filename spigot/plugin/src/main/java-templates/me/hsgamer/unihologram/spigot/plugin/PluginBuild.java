package me.hsgamer.unihologram.spigot.plugin;

final class PluginBuild {
    static final String NAME = "UniHologram";
    static final String VERSION = "${project.version}";
    static final String DESCRIPTION = "${project.description}";
    static final String AUTHOR = "HSGamer";

    private PluginBuild() {
        throw new IllegalStateException("Utility class");
    }
}
