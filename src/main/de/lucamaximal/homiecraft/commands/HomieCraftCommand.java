package de.lucamaximal.homiecraft.commands;

import de.lucamaximal.homiecraft.Main;
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

        if(args.length == 1 && args[0].equalsIgnoreCase("reload")) {

            plugin.reloadConfig();
            sender.sendMessage("§aConfig neu geladen!");

            return true;
        }

        sender.sendMessage("§cBenutzung: /homiecraft reload");

        return true;
    }
}
