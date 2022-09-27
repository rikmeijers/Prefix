package me.rik02.prefix.modules.prefix;

import me.rik02.prefix.PrefixPlugin;
import me.rik02.prefix.modules.prefix.commands.PrefixCommand;
import me.rik02.prefix.modules.prefix.events.PlayerChat;
import me.rik02.prefix.modules.prefix.events.PlayerJoin;
import me.rik02.prefix.modules.prefix.events.PlayerQuit;
import me.rik02.prefix.modules.prefix.objects.Tablist;
import me.rik02.prefix.objects.AbstractModule;
import me.rik02.prefix.objects.File;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PrefixModule extends AbstractModule<PrefixPlugin> {
    // Properties
    public final File groups;
    public final File players;
    public final Tablist tablist;

    // Constructor
    public PrefixModule(PrefixPlugin plugin) {
        super(plugin);
        groups = new File("groups");
        players = new File("players");
        tablist = new Tablist(this);
    }

    // Methods
    @Override
    protected void onEnable() {
        command(() -> new PrefixCommand(this));
        listen(() -> new PlayerChat(this));
        listen(() -> new PlayerJoin(this));
        listen(() -> new PlayerQuit(this));

        for (Player player : Bukkit.getOnlinePlayers()) {
            tablist.update(player);
        }
    }
}
