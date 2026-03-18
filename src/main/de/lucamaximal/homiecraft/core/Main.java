package de.lucamaximal.homiecraft.core;

import de.lucamaximal.homiecraft.commands.CommandManager;
import de.lucamaximal.homiecraft.placeholder.HomieCraftExpansion;
import de.lucamaximal.homiecraft.tpa.TpaManager;
import de.lucamaximal.homiecraft.util.MessageManager;
import de.lucamaximal.homiecraft.util.PlaceholderManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;

    private MessageManager messageManager;
    private PlaceholderManager placeholderManager;
    private TpaManager tpaManager;

    @Override
    public void onEnable() {

        instance = this;

        saveDefaultConfig();

        this.placeholderManager = new PlaceholderManager(this);
        this.messageManager = new MessageManager(this);
        this.tpaManager = new TpaManager(this);

        new CommandManager(this).registerCommands();

        if (getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new HomieCraftExpansion(this).register();
        }

        // ✅ Config Message statt hardcoded
        getLogger().info(messageManager.getRaw("plugin_enable"));
    }

    @Override
    public void onDisable() {
        getLogger().info(messageManager.getRaw("plugin_disable"));
    }

    // =========================
    // GETTER
    // =========================

    public static Main getInstance() {
        return instance;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }

    public PlaceholderManager getPlaceholderManager() {
        return placeholderManager;
    }

    public TpaManager getTpaManager() {
        return tpaManager;
    }

    // =========================
    // RELOAD
    // =========================

    public void reloadPlugin() {

        reloadConfig();

        this.placeholderManager = new PlaceholderManager(this);
        this.messageManager = new MessageManager(this);

        getLogger().info(messageManager.getRaw("plugin_enable"));
    }
}
