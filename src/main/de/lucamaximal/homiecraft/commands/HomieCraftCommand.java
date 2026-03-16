package de.lucamaximal.homiecraft.commands;

import de.lucamaximal.homiecraft.Main;
import de.lucamaximal.homiecraft.util.MessageUtils;
import de.lucamaximal.homiecraft.util.Messages;
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

        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {

            // Reload config
            plugin.reloadConfig();

            // Sende Nachricht mit MiniMessage
            MessageUtils.send(sender, Messages.RELOAD);
            return true;
        }

        // Hilfe / Usage Message
        MessageUtils.send(sender, Messages.RELOAD); // Optional: eigenen Help-Text definieren
        return true;
    }
}
