package de.lucamaximal.homiecraft.commands.general.subcommands;

import de.lucamaximal.homiecraft.core.Main;
import org.bukkit.command.CommandSender;

public class HelpSubCommand implements SubCommand {

    private final Main plugin;

    public HelpSubCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getPermission() {
        return null; // jeder darf
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage(plugin.getMessageManager().getMessage("help"));
    }
}
