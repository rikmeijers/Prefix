package me.rik02.prefix.objects;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;
import java.util.logging.Level;

import static java.util.Objects.requireNonNull;

public abstract class AbstractModule<P extends Plugin> {
    // Properties
    private final P plugin;
    private final String name;
    private final AtomicBoolean enabled;
    private final List<Listener> listeners;
    private final List<Command> commands;
    private final CommandMap commandMap;

    // Constructor
    public AbstractModule(P plugin) {
        CommandMap tempCommandMap = null;
        this.plugin = plugin;
        name = getClass().getSimpleName();
        enabled = new AtomicBoolean();
        listeners = new LinkedList<>();
        commands = new LinkedList<>();

        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            tempCommandMap = (CommandMap) bukkitCommandMap.get(plugin.getServer());
        } catch (NoSuchFieldException | IllegalAccessException exception) {
            Bukkit.shutdown();
        }
        commandMap = tempCommandMap;
    }

    // Methods
    public final void load() {
        if (!enabled.compareAndSet(false, true)) {
            throw new RuntimeException("Attempt to load module while already enabled.");
        }

        new Log("Loading " + name + "...");
        onLoad();
    }

    public final boolean enable() {
        if (!enabled.get()) {
            throw new RuntimeException("Attempt to enable module while not loaded.");
        }

        new Log("Enabling " + name + "...");
        try {
            onEnable();
        } catch (Exception exception) {
            new Log(Level.SEVERE, exception.getMessage());
            Bukkit.shutdown();
            return false;
        }

        return true;
    }

    public final void disable() {
        if (!enabled.compareAndSet(true, false)) {
            throw new RuntimeException("Attempt to disable module while not enabled.");
        }

        int components = listeners.size() + commands.size();
        new Log("Disabling " + name + ", removing " + components + "(+) components...");
        listeners.forEach(HandlerList::unregisterAll);
        listeners.clear();
        commands.forEach((command) -> command.unregister(commandMap));
        commands.clear();
        onDisable();
    }

    protected void onLoad() {}
    protected void onEnable() throws Exception {}
    protected void onDisable() {}

    public final void listen(Supplier<? extends Listener> supplier) {
        if (!enabled.get()) {
            throw new RuntimeException("Module is not enabled.");
        }

        Listener listener = requireNonNull(requireNonNull(supplier, "Supplier should not be null.").get(), "Supplied listener should not be null.");
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
        listeners.add(listener);
    }

    public final void command(Supplier<Object> supplier) {
        if (!enabled.get()) {
            throw new RuntimeException("Module is not enabled.");
        }

        Object object = requireNonNull(requireNonNull(supplier, "Supplier should not be null.").get(), "Supplied command should not be null.");
        AbstractCommand abstractCommand = (AbstractCommand) object;
        commandMap.register(abstractCommand.getName(), abstractCommand);
        commands.add(abstractCommand);
    }

    public final P getPlugin() {
        return plugin;
    }

    public final String getName() {
        return name;
    }

    public final boolean getEnabled() {
        return enabled.get();
    }
}
