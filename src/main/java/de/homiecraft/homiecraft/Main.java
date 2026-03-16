package de.homiecraft.homiecraft;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Plugin wurde gestartet!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin wurde gestoppt!");
    }
}
