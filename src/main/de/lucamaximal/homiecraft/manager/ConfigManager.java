package de.lucamaximal.homiecraft.manager;

import de.lucamaximal.homiecraft.Main;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private final Main plugin;

    public ConfigManager(Main plugin) {
        this.plugin = plugin;
    }

    public void reload() {
        plugin.reloadConfig();
    }

    public FileConfiguration getConfig() {
        return plugin.getConfig();
    }

}
