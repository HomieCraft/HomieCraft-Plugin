package de.lucamaximal.homiecraft.core;

import de.lucamaximal.homiecraft.config.MessageManager;
import de.lucamaximal.homiecraft.listener.JoinListener;
import de.lucamaximal.homiecraft.listener.LeaveListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main instance;
    private MessageManager messageManager;

    @Override
    public void onEnable() {

        instance = this;

        // Config laden
        saveDefaultConfig();

        // MessageManager initialisieren
        messageManager = new MessageManager(getConfig());

        // Listener registrieren
        getServer().getPluginManager().registerEvents(
                new JoinListener(messageManager), this
        );

        getServer().getPluginManager().registerEvents(
                new LeaveListener(messageManager), this
        );

        getLogger().info("HomieCraft Plugin wurde gestartet!");
    }

    @Override
    public void onDisable() {
        getLogger().info("HomieCraft Plugin wurde gestoppt!");
    }

    // Zugriff auf Instanz (optional aber praktisch)
    public static Main getInstance() {
        return instance;
    }

    // Zugriff auf MessageManager
    public MessageManager getMessageManager() {
        return messageManager;
    }
}
