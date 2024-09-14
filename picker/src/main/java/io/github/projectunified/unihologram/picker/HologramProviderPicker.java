package io.github.projectunified.unihologram.picker;

import io.github.projectunified.unihologram.api.HologramProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * A picker for {@link HologramProvider}.
 * This preserves the order of the entries based on the order of the calls to the add method.
 *
 * @param <I> The type of the input
 * @param <T> The type of the location of the hologram
 */
public class HologramProviderPicker<I, T> {
    private final I input;
    private final List<Entry<I, T>> entries = new ArrayList<>();

    /**
     * Create a new picker
     *
     * @param input the input
     */
    public HologramProviderPicker(I input) {
        this.input = input;
    }

    /**
     * Add an entry
     *
     * @param condition the condition
     * @param provider  the provider
     * @return this
     */
    public HologramProviderPicker<I, T> add(Predicate<I> condition, Function<I, HologramProvider<T>> provider) {
        entries.add(new Entry<>(condition, provider));
        return this;
    }

    /**
     * Add an entry
     *
     * @param condition the condition
     * @param provider  the provider
     * @return this
     */
    public HologramProviderPicker<I, T> add(BooleanSupplier condition, Function<I, HologramProvider<T>> provider) {
        return add(i -> condition.getAsBoolean(), provider);
    }

    /**
     * Add an entry
     *
     * @param condition the condition
     * @param provider  the provider
     * @return this
     */
    public HologramProviderPicker<I, T> add(Predicate<I> condition, Supplier<HologramProvider<T>> provider) {
        return add(condition, i -> provider.get());
    }

    /**
     * Add an entry
     *
     * @param condition the condition
     * @param provider  the provider
     * @return this
     */
    public HologramProviderPicker<I, T> add(BooleanSupplier condition, Supplier<HologramProvider<T>> provider) {
        return add(i -> condition.getAsBoolean(), i -> provider.get());
    }

    /**
     * Pick the provider
     *
     * @return the provider
     */
    public HologramProvider<T> pick() {
        for (Entry<I, T> entry : entries) {
            if (entry.condition.test(input)) {
                return entry.provider.apply(input);
            }
        }
        return getDefaultProvider(input);
    }

    /**
     * Get the default provider.
     * By default, this throws an exception.
     *
     * @param input the input
     * @return the default provider
     */
    protected HologramProvider<T> getDefaultProvider(I input) {
        throw new IllegalStateException("No default provider is set");
    }

    private static class Entry<I, T> {
        private final Predicate<I> condition;
        private final Function<I, HologramProvider<T>> provider;

        private Entry(Predicate<I> condition, Function<I, HologramProvider<T>> provider) {
            this.condition = condition;
            this.provider = provider;
        }
    }
}
