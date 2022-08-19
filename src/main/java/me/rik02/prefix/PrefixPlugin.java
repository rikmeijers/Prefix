package me.rik02.prefix;

import me.rik02.prefix.modules.prefix.PrefixModule;
import me.rik02.prefix.objects.ModuleManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class PrefixPlugin extends JavaPlugin {
    // Properties
    private final ModuleManager<PrefixPlugin> moduleManager = new ModuleManager<>();
    private static PrefixPlugin instance;

    @Override
    public void onLoad() {
        instance = this;
        moduleManager.prepare(new PrefixModule(this));
        moduleManager.load();
    }

    @Override
    public void onEnable() {
        moduleManager.enable();
    }

    @Override
    public void onDisable() {
        moduleManager.disable();
    }

    public static PrefixPlugin getInstance() {
        return instance;
    }
}
