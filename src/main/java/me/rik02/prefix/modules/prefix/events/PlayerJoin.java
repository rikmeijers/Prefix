package me.rik02.prefix.modules.prefix.events;

import me.rik02.prefix.modules.prefix.PrefixModule;
import me.rik02.prefix.modules.prefix.objects.Tablist;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
    // Properties
    private final PrefixModule module;

    // Constructor
    public PlayerJoin(PrefixModule module) {
        this.module = module;
    }

    // Methods
    @EventHandler
    public void playerJoinListener(PlayerJoinEvent event) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            module.tablist.update(player);
        }
    }
}
