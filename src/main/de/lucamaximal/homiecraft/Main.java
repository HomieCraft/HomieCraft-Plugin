package de.lucamaximal.homiecraft;

import de.lucamaximal.homiecraft.commands.CommandManager;
import de.lucamaximal.homiecraft.listener.JoinListener;
import de.lucamaximal.homiecraft.manager.ConfigManager;
import de.lucamaximal.homiecraft.manager.MessageManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main instance;

    private ConfigManager configManager;
    private MessageManager messageManager;
    private CommandManager commandManager;

    @Override
    public void onEnable() {

        instance = this;

        saveDefaultConfig();

        configManager = new ConfigManager(this);
        messageManager = new MessageManager(this);
        commandManager = new CommandManager(this);

        registerListeners();
        commandManager.registerCommands();

        getLogger().info("HomieCraft Plugin gestartet!");
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
    }

    public static Main getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }

}
