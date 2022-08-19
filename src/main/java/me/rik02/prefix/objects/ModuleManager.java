package me.rik02.prefix.objects;

import org.bukkit.plugin.Plugin;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Objects.requireNonNull;

public final class ModuleManager<P extends Plugin> {
    // Enum
    private enum State {
        IDLE, LOAD, ENABLE, DISABLE
    }

    // Properties
    private final Map<Class<? extends AbstractModule>, AbstractModule<P>> modules;
    private final AtomicReference<State> state;

    // Constructor
    public ModuleManager() {
        modules = new LinkedHashMap<>();
        state = new AtomicReference<>(State.IDLE);
    }

    // Methods
    public void prepare(AbstractModule<P> module) {
        requireNonNull(module);

        if (!modules.containsKey(module.getClass())) {
            modules.put(module.getClass(), module);
        }
    }

    public void load() {
        if (!state.compareAndSet(State.IDLE, State.LOAD)) {
            throw new IllegalStateException("Manager isn't idle.");
        }

        modules.values().forEach(AbstractModule::load);
    }

    public void enable() {
        if (!state.compareAndSet(State.LOAD, State.ENABLE)) {
            throw new IllegalStateException("Manager has not been loaded.");
        }
        for (AbstractModule<P> module : modules.values()) {
            if (!module.enable()) return;
        }
    }

    public void disable() {
        if (!state.compareAndSet(State.ENABLE, State.DISABLE)) {
            throw new IllegalStateException("Manager has not been enabled.");
        }

        modules.values().forEach(AbstractModule::disable);
    }
}
