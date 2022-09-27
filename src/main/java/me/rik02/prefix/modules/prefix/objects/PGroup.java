package me.rik02.prefix.modules.prefix.objects;

import me.rik02.prefix.modules.prefix.PrefixModule;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class PGroup {
    // Properties
    private final PrefixModule module;
    private String name;
    private String chatPrefix;
    private String tabPrefix;
    private String chatNameColor;
    private String tabNameColor;
    private String chatColor;
    private int tabWeight;

    // Constructors
    public PGroup(String name, PrefixModule module) {
        this.module = module;
        this.name = name;

        if (!module.groups.getConfig().contains(name)) {
            module.groups.getConfig().set(name + ".chatPrefix", "");
            module.groups.getConfig().set(name + ".tabPrefix", "");
            module.groups.getConfig().set(name + ".chatNameColor", "&7");
            module.groups.getConfig().set(name + ".tabNameColor", "&7");
            module.groups.getConfig().set(name + ".chatColor", "&7");
            module.groups.getConfig().set(name + ".tabWeight", 0);
            module.groups.saveConfig();
        }
        this.chatPrefix = module.groups.getConfig().getString(name + ".chatPrefix");
        this.tabPrefix = module.groups.getConfig().getString(name + ".tabPrefix");
        this.chatNameColor = module.groups.getConfig().getString(name + ".chatNameColor");
        this.tabNameColor = module.groups.getConfig().getString(name + ".tabNameColor");
        this.chatColor = module.groups.getConfig().getString(name + ".chatColor");
        this.tabWeight = module.groups.getConfig().getInt(name + ".tabWeight");
    }

    public PGroup(String name, String chatPrefix, String tabPrefix, ChatColor chatNameColor, ChatColor tabNameColor, ChatColor chatColor, int tabWeight, PrefixModule module) {
        this.module = module;
        this.name = name;
        this.chatPrefix = chatPrefix;
        this.tabPrefix = tabPrefix;
        this.chatNameColor = "&" + chatNameColor.getChar();
        this.tabNameColor = "&" + tabNameColor.getChar();
        this.chatColor = "&" + chatColor.getChar();
        this.tabWeight = tabWeight;

        module.groups.getConfig().set(name + ".chatPrefix", chatPrefix);
        module.groups.getConfig().set(name + ".tabPrefix", tabPrefix);
        module.groups.getConfig().set(name + ".chatNameColor", this.chatNameColor);
        module.groups.getConfig().set(name + ".tabNameColor", this.tabNameColor);
        module.groups.getConfig().set(name + ".chatColor", this.chatColor);
        module.groups.getConfig().set(name + ".tabWeight", tabWeight);
        module.groups.saveConfig();
    }

    // Methods
    public String getChatPrefix() {
        return chatPrefix;
    }

    public String getTabPrefix() {
        return tabPrefix;
    }

    public String getChatNameColor() {
        return chatNameColor.replace('&', '§');
    }

    public String getTabNameColor() {
        return tabNameColor.replace('&', '§');
    }

    public String getChatColor() {
        return chatColor.replace('&', '§');
    }

    public int getTabWeight() {
        return tabWeight;
    }

    public String getName() {
        return name;
    }

    public void setChatPrefix(String chatPrefix) {
        this.chatPrefix = chatPrefix;
        module.groups.getConfig().set(name + ".chatPrefix", chatPrefix);
        module.groups.saveConfig();
    }

    public void setTabPrefix(String tabPrefix) {
        this.tabPrefix = tabPrefix;
        module.groups.getConfig().set(name + ".tabPrefix", tabPrefix);
        module.groups.saveConfig();

        for (Player player : Bukkit.getOnlinePlayers()) {
            module.tablist.update(player);
        }
    }

    public void setChatNameColor(String chatNameColor) {
        this.chatNameColor = chatNameColor.replace('&', '§');
        module.groups.getConfig().set(name + ".chatNameColor", chatNameColor);
        module.groups.saveConfig();
    }

    public void setTabNameColor(String tabNameColor) {
        this.tabNameColor = tabNameColor.replace('&', '§');
        module.groups.getConfig().set(name + ".tabNameColor", tabNameColor);
        module.groups.saveConfig();

        for (Player player : Bukkit.getOnlinePlayers()) {
            module.tablist.update(player);
        }
    }

    public void setChatColor(String chatColor) {
        this.chatColor = chatColor.replace('&', '§');
        module.groups.getConfig().set(name + ".chatColor", chatColor);
        module.groups.saveConfig();
    }

    public void setTabWeight(int tabWeight) {
        this.tabWeight = tabWeight;
        module.groups.getConfig().set(name + ".tabWeight", tabWeight);
        module.groups.saveConfig();

        for (Player player : Bukkit.getOnlinePlayers()) {
            module.tablist.update(player);
        }
    }

    public void setName(String name) {
        this.name = name;
    }
}
