package me.rik02.prefix.modules.prefix;

import lombok.Getter;
import me.rik02.prefix.PrefixPlugin;
import me.rik02.prefix.objects.AbstractModule;
import me.rik02.prefix.objects.File;

@Getter
public class PrefixModule extends AbstractModule<PrefixPlugin> {
    // Properties
    private final File groups;
    private final File users;

    // Constructor
    public PrefixModule(PrefixPlugin plugin) {
        super(plugin);
        groups = new File("groups");
        users = new File("users");
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }
}
