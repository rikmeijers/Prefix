package me.rik02.prefix.modules.prefix.objects;

import me.rik02.prefix.modules.prefix.PrefixModule;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class PPlayer {
    // Properties
    private final PrefixModule module;
    private String name;
    private String groupName;
    public final boolean hasJoinedBefore;

    // Constructor
    public PPlayer(String name, PrefixModule module) {
        this.module = module;
        this.name = name;
        hasJoinedBefore = module.players.getConfig().contains(name);

        if (!hasJoinedBefore) {
            module.players.getConfig().set(name + ".group", "");
            module.players.saveConfig();
            groupName = "";
        } else {
            groupName = module.players.getConfig().getString(name + ".group");
        }
    }

    // Methods
    public String getName() {
        return name;
    }

    public String getGroupName() {
        return groupName;
    }

    public PGroup getPGroup() {
        if (!module.groups.getConfig().contains(groupName)) return null;
        return new PGroup(groupName, module);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPGroup(String groupName) {
        this.groupName = groupName;

        module.players.getConfig().set(name + ".group", groupName);
        module.players.saveConfig();

        for (Player player : Bukkit.getOnlinePlayers()) {
            module.tablist.update(player);
        }
    }
}
