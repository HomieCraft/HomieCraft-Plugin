package de.lucamaximal.homiecraft;

import de.lucamaximal.homiecraft.listener.JoinListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {

        instance = this;

        saveDefaultConfig();

        registerListeners();

        getLogger().info("HomieCraft Plugin gestartet!");
    }

    @Override
    public void onDisable() {
        getLogger().info("HomieCraft Plugin gestoppt!");
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
    }

    public static Main getInstance() {
        return instance;
    }
}
