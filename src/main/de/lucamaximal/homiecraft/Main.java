package de.lucamaximal.homiecraft;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("HomieCraft Plugin gestartet!");
    }

    @Override
    public void onDisable() {
        getLogger().info("HomieCraft Plugin gestoppt!");
    }
}
