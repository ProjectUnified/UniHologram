package io.github.projectunified.unihologram.spigot.fancyholograms;

import com.google.common.base.Preconditions;
import de.oliver.fancyholograms.api.FancyHologramsPlugin;
import de.oliver.fancyholograms.api.data.DisplayHologramData;
import de.oliver.fancyholograms.api.data.HologramData;
import de.oliver.fancyholograms.api.data.TextHologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;
import io.github.projectunified.unihologram.api.HologramLine;
import io.github.projectunified.unihologram.api.display.DisplayBillboard;
import io.github.projectunified.unihologram.api.display.DisplayHologram;
import io.github.projectunified.unihologram.api.display.DisplayScale;
import io.github.projectunified.unihologram.api.display.DisplayTextAlignment;
import io.github.projectunified.unihologram.spigot.api.visibility.PlayerVisibility;
import io.github.projectunified.unihologram.spigot.line.TextHologramLine;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * The hologram for FancyHolograms
 */
public class FHHologram implements PlayerVisibility, DisplayHologram<Location> {
    private final Hologram hologram;

    /**
     * Create a new hologram
     *
     * @param name     the name of the hologram
     * @param location the location of the hologram
     */
    public FHHologram(String name, Location location) {
        HologramData data = new TextHologramData(name, location);
        data.setPersistent(false);
        this.hologram = FancyHologramsPlugin.get().getHologramManager().create(data);
    }

    /**
     * Create a new hologram
     *
     * @param hologram the hologram
     */
    public FHHologram(Hologram hologram) {
        this.hologram = hologram;
    }

    private void checkHologramInitialized() {
        Preconditions.checkArgument(isInitialized(), "Hologram is not initialized");
    }

    private void updateHologram() {
        hologram.forceUpdate();
        hologram.refreshForViewersInWorld();
    }

    private String toText(HologramLine line) {
        if (line instanceof TextHologramLine) {
            String content = ((TextHologramLine) line).getContent();
            boolean isMini = Boolean.parseBoolean(Objects.toString(line.getSettings().getOrDefault("mini", "false")));
            if (isMini) {
                return content;
            } else {
                Component component = LegacyComponentSerializer.legacySection().deserialize(content);
                return MiniMessage.miniMessage().serializeOr(component, content);
            }
        }
        return line.getRawContent();
    }

    private List<String> toText(List<HologramLine> lines) {
        return lines.stream().map(this::toText).collect(Collectors.toList());
    }

    @Override
    public @NotNull List<HologramLine> getLines() {
        checkHologramInitialized();
        TextHologramData data = (TextHologramData) hologram.getData();
        return data.getText().stream().<HologramLine>map(TextHologramLine::new).collect(Collectors.toList());
    }

    @Override
    public void setLines(@NotNull List<HologramLine> lines) {
        checkHologramInitialized();
        List<String> text = toText(lines);
        ((TextHologramData) hologram.getData()).setText(text);
        updateHologram();
    }

    private void editLine(Consumer<List<HologramLine>> consumer) {
        checkHologramInitialized();
        List<HologramLine> lines = new ArrayList<>(getLines());
        consumer.accept(lines);
        setLines(lines);
    }

    @Override
    public void addLine(@NotNull HologramLine line) {
        editLine(lines -> lines.add(line));
    }

    @Override
    public void insertLine(int index, @NotNull HologramLine line) {
        editLine(lines -> lines.add(index, line));
    }

    @Override
    public void removeLine(int index) {
        editLine(lines -> lines.remove(index));
    }

    @Override
    public String getName() {
        return hologram.getData().getName();
    }

    @Override
    public void init() {
        hologram.createHologram();
        Bukkit.getOnlinePlayers().forEach(hologram::updateShownStateFor);
        FancyHologramsPlugin.get().getHologramManager().addHologram(hologram);
    }

    @Override
    public void clear() {
        FancyHologramsPlugin.get().getHologramManager().removeHologram(hologram);
    }

    @Override
    public boolean isInitialized() {
        return FancyHologramsPlugin.get().getHologramManager().getHolograms().contains(hologram);
    }

    @Override
    public Location getLocation() {
        checkHologramInitialized();
        return hologram.getData().getLocation();
    }

    @Override
    public void setLocation(Location location) {
        checkHologramInitialized();
        hologram.getData().setLocation(location);
        updateHologram();
    }

    @Override
    public boolean isVisible(Player viewer) {
        checkHologramInitialized();
        return hologram.isViewer(viewer);
    }

    @Override
    public void showAll() {
        checkHologramInitialized();
        hologram.showHologram(Bukkit.getOnlinePlayers());
    }

    @Override
    public void hideAll() {
        checkHologramInitialized();
        hologram.hideHologram(Bukkit.getOnlinePlayers());
    }

    @Override
    public void showTo(Player viewer) {
        checkHologramInitialized();
        hologram.showHologram(viewer);
    }

    @Override
    public void hideTo(Player viewer) {
        checkHologramInitialized();
        hologram.hideHologram(viewer);
    }

    @Override
    public Color getBackgroundColor() {
        checkHologramInitialized();
        org.bukkit.Color background = ((TextHologramData) hologram.getData()).getBackground();

        return background != null ? new Color(background.getRed(), background.getGreen(), background.getBlue()) : null;
    }

    @Override
    public void setBackgroundColor(Color color) {
        checkHologramInitialized();
        ((TextHologramData) hologram.getData()).setBackground(org.bukkit.Color.fromRGB(
                color.getRed(),
                color.getGreen(),
                color.getBlue()));
        updateHologram();
    }

    @Override
    public DisplayScale getScale() {
        checkHologramInitialized();
        DisplayHologramData data = (DisplayHologramData) hologram.getData();
        Vector3f scale = data.getScale();
        return new DisplayScale(scale.x(), scale.y(), scale.z());
    }

    @Override
    public void setScale(DisplayScale scale) {
        checkHologramInitialized();
        Vector3f vectorScale = new Vector3f(scale.x, scale.y, scale.z);
        DisplayHologramData data = (DisplayHologramData) hologram.getData();
        data.setScale(vectorScale);
        updateHologram();
    }

    @Override
    public float getShadowRadius() {
        checkHologramInitialized();
        return ((DisplayHologramData) hologram.getData()).getShadowRadius();
    }

    @Override
    public void setShadowRadius(float radius) {
        checkHologramInitialized();
        ((DisplayHologramData) hologram.getData()).setShadowRadius(radius);
        updateHologram();
    }

    @Override
    public float getShadowStrength() {
        checkHologramInitialized();
        return ((DisplayHologramData) hologram.getData()).getShadowStrength();
    }

    @Override
    public void setShadowStrength(float strength) {
        checkHologramInitialized();
        ((DisplayHologramData) hologram.getData()).setShadowStrength(strength);
        updateHologram();
    }

    @Override
    public boolean isShadowed() {
        checkHologramInitialized();
        TextHologramData data = (TextHologramData) hologram.getData();

        return data.hasTextShadow();
    }

    @Override
    public void setShadowed(boolean shadowed) {
        checkHologramInitialized();
        TextHologramData data = (TextHologramData) hologram.getData();
        data.setTextShadow(shadowed);
        updateHologram();
    }

    @Override
    public DisplayBillboard getBillboard() {
        checkHologramInitialized();
        switch (((DisplayHologramData) hologram.getData()).getBillboard()) {
            case FIXED:
                return DisplayBillboard.FIXED;
            case VERTICAL:
                return DisplayBillboard.VERTICAL;
            case HORIZONTAL:
                return DisplayBillboard.HORIZONTAL;
            default:
                return DisplayBillboard.CENTER;
        }
    }

    @Override
    public void setBillboard(DisplayBillboard billboard) {
        checkHologramInitialized();
        DisplayHologramData data = (DisplayHologramData) hologram.getData();
        switch (billboard) {
            case FIXED:
                data.setBillboard(Display.Billboard.FIXED);
                break;
            case VERTICAL:
                data.setBillboard(Display.Billboard.VERTICAL);
                break;
            case HORIZONTAL:
                data.setBillboard(Display.Billboard.HORIZONTAL);
                break;
            default:
                data.setBillboard(Display.Billboard.CENTER);
                break;
        }
        updateHologram();
    }

    @Override
    public DisplayTextAlignment getAlignment() {
        checkHologramInitialized();
        TextHologramData data = (TextHologramData) hologram.getData();

        switch (data.getTextAlignment()) {
            case LEFT:
                return DisplayTextAlignment.LEFT;
            case RIGHT:
                return DisplayTextAlignment.RIGHT;
            default:
                return DisplayTextAlignment.CENTER;
        }
    }

    @Override
    public void setAlignment(DisplayTextAlignment alignment) {
        checkHologramInitialized();
        TextHologramData data = (TextHologramData) hologram.getData();

        switch (alignment) {
            case LEFT:
                data.setTextAlignment(TextDisplay.TextAlignment.LEFT);
                break;
            case RIGHT:
                data.setTextAlignment(TextDisplay.TextAlignment.RIGHT);
                break;
            default:
                data.setTextAlignment(TextDisplay.TextAlignment.CENTER);
                break;
        }
        updateHologram();
    }
}
