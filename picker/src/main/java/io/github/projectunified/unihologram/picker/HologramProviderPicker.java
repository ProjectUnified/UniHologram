package io.github.projectunified.unihologram.picker;

import io.github.projectunified.unihologram.api.Hologram;
import io.github.projectunified.unihologram.api.HologramProvider;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
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
 * @param <B> The type of the builder
 */
public class HologramProviderPicker<I, T, B extends HologramProviderPicker<I, T, B>> {
    private final I input;
    private final List<Entry<I, T>> entries = new ArrayList<>();

    /**
     * Create a new picker
     *
     * @param input the input
     */
    protected HologramProviderPicker(I input) {
        this.input = input;
    }

    @SuppressWarnings("unchecked")
    private B self() {
        return (B) this;
    }

    private HologramProvider<T> exceptionHologramProvider() {
        return new HologramProvider<T>() {
            private final UnsupportedOperationException exception = new UnsupportedOperationException("This is an exception hologram provider. If you see this, that means your hologram provider picker is not working correctly");

            @Override
            public @NotNull Hologram<T> createHologram(@NotNull String name, @NotNull T location) {
                throw exception;
            }

            @Override
            public Optional<Hologram<T>> getHologram(@NotNull String name) {
                throw exception;
            }

            @Override
            public Collection<Hologram<T>> getAllHolograms() {
                throw exception;
            }
        };
    }

    /**
     * Add an entry
     *
     * @param condition the condition
     * @param provider  the provider
     * @return this
     */
    public B add(Predicate<I> condition, Function<I, HologramProvider<T>> provider) {
        entries.add(new Entry<>(condition, provider));
        return self();
    }

    /**
     * Add an entry
     *
     * @param condition the condition
     * @param provider  the provider
     * @return this
     */
    public B add(BooleanSupplier condition, Function<I, HologramProvider<T>> provider) {
        return add(i -> condition.getAsBoolean(), provider);
    }

    /**
     * Add an entry
     *
     * @param condition the condition
     * @param provider  the provider
     * @return this
     */
    public B add(Predicate<I> condition, Supplier<HologramProvider<T>> provider) {
        return add(condition, i -> provider.get());
    }

    /**
     * Add an entry
     *
     * @param condition the condition
     * @param provider  the provider
     * @return this
     */
    public B add(BooleanSupplier condition, Supplier<HologramProvider<T>> provider) {
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
     * Get the default provider
     *
     * @param input the input
     * @return the default provider
     */
    protected HologramProvider<T> getDefaultProvider(I input) {
        return exceptionHologramProvider();
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
