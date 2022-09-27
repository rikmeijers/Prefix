package me.rik02.prefix.objects;

import me.rik02.prefix.PrefixPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public final class File {
    // Properties
    private final PrefixPlugin plugin = PrefixPlugin.getInstance();
    private final String fileName;
    private FileConfiguration configuration = null;
    private java.io.File file = null;

    // Constructor
    public File(String fileName) {
        this.fileName = fileName + ".yml";
        saveDefaultConfig();
    }

    // Methods
    public void reloadConfig() {
        if (file ==  null) {
            file = new java.io.File(plugin.getDataFolder(), fileName);
        }

        configuration = YamlConfiguration.loadConfiguration(file);
        InputStream inputStream = plugin.getResource(fileName);
        if (inputStream != null) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(new InputStreamReader(inputStream));
            configuration.setDefaults(config);
        }
    }

    public FileConfiguration getConfig() {
        if (configuration == null) {
            reloadConfig();
        }

        return configuration;
    }

    public void saveConfig() {
        if (configuration == null || file == null) {
            return;
        }

        try {
            getConfig().save(file);
        } catch (Exception exception) {
            new Log(Level.WARNING, "Could not save config to " + file.getName());
            new Log(Level.WARNING, exception.getMessage());
        }
    }

    public void saveDefaultConfig() {
        if (file == null) {
            file = new java.io.File(plugin.getDataFolder(), fileName);
        }
        if (!file.exists()) {
            plugin.saveResource(fileName, false);
        }
    }
}
