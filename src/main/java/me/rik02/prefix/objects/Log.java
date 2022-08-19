package me.rik02.prefix.objects;

import me.rik02.prefix.PrefixPlugin;

import java.util.logging.Level;

public final class Log {
    // Constructors
    public Log(String message) {
        PrefixPlugin.getInstance().getServer().getLogger().log(Level.INFO, message);
    }

    public Log(Level level, String message) {
        PrefixPlugin.getInstance().getServer().getLogger().log(level, message);
    }
}
