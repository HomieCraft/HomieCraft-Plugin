package de.lucamaximal.homiecraft.core;

import de.lucamaximal.homiecraft.config.MessageManager;
import de.lucamaximal.homiecraft.listener.JoinListener;
import de.lucamaximal.homiecraft.listener.LeaveListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main instance;
    private MessageManager messageManager;

    @Override
    public void onEnable() {

        instance = this;

        saveDefaultConfig();

        messageManager = new MessageManager(getConfig());

        // Listener registrieren
        getServer().getPluginManager().registerEvents(
                new JoinListener(messageManager), this
        );

        getServer().getPluginManager().registerEvents(
                new LeaveListener(messageManager), this
        );

        // Plugin Start Message (für Konsole + Server)
        Bukkit.getConsoleSender().sendMessage(messageManager.getMessage("plugin_enable"));
        //Commands
        getCommand("spawn").setExecutor(
        new SpawnCommand(this, messageManager)
        );
        
        getCommand("setspawn").setExecutor(
            new SetSpawnCommand(this, messageManager)
        );
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(messageManager.getMessage("plugin_disable"));
    }

    public static Main getInstance() {
        return instance;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }
}
