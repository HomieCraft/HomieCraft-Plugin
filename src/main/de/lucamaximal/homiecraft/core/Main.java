package de.lucamaximal.homiecraft.core;

import de.lucamaximal.homiecraft.commands.CommandManager;
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

        // Commands registrieren
        new CommandManager(this).registerCommands();

        // Start Message
        Bukkit.getConsoleSender().sendMessage(
                messageManager.getMessage("plugin_enable")
        );
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(
                messageManager.getMessage("plugin_disable")
        );
    }

    // 🔥 Reload Methode (sehr wichtig)
    public void reloadPlugin() {

        // Config neu laden
        reloadConfig();

        // MessageManager neu erstellen (wichtig!)
        messageManager = new MessageManager(getConfig());

        // Optional: hier später weitere Systeme reloaden
    }

    public static Main getInstance() {
        return instance;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }
}
