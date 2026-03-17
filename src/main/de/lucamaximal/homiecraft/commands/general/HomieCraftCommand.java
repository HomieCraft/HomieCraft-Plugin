package de.lucamaximal.homiecraft.commands.general;

import de.lucamaximal.homiecraft.config.MessageManager;
import de.lucamaximal.homiecraft.core.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HomieCraftCommand implements CommandExecutor {

    private final Main plugin;
    private final MessageManager messageManager;

    public HomieCraftCommand(Main plugin) {
        this.plugin = plugin;
        this.messageManager = plugin.getMessageManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // /homiecraft reload
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {

            plugin.reloadConfig();

            sender.sendMessage(messageManager.getMessage("plugin_enable")); // kannst du später ändern
            return true;
        }

        // Default / Hilfe
        sender.sendMessage(messageManager.getMessage("help"));
        return true;
    }
}
