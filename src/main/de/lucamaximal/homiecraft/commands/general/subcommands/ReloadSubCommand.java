package de.lucamaximal.homiecraft.commands.general.subcommands;

import de.lucamaximal.homiecraft.core.Main;
import org.bukkit.command.CommandSender;

public class ReloadSubCommand implements SubCommand {

    private final Main plugin;

    public ReloadSubCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getPermission() {
        return "homiecraft.reload";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (!sender.hasPermission(getPermission())) {
            sender.sendMessage(plugin.getMessageManager().getMessage("no_permission"));
            return;
        }

        plugin.reloadPlugin();
        sender.sendMessage(plugin.getMessageManager().getMessage("reload"));
    }
}
