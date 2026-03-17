package de.lucamaximal.homiecraft.commands.general;

import de.lucamaximal.homiecraft.core.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HomieCraftCommand implements CommandExecutor {

    private final Main plugin;

    public HomieCraftCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // /hc reload
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {

            plugin.reloadPlugin();

            sender.sendMessage(plugin.getMessageManager().getMessage("plugin_enable"));
            return true;
        }

        // Default (Help)
        sender.sendMessage(plugin.getMessageManager().getMessage("help"));
        return true;
    }
}
