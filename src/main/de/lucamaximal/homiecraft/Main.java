package de.lucamaximal.homiecraft;

import de.lucamaximal.homiecraft.listener.JoinListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {

        getLogger().info("HomieCraft Plugin gestartet!");

        getServer().getPluginManager().registerEvents(new JoinListener(), this);

    }

    @Override
    public void onDisable() {
        getLogger().info("HomieCraft Plugin gestoppt!");
    }

}
