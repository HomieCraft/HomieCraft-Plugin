package de.lucamaximal.homiecraft.commands.general;

import de.lucamaximal.homiecraft.commands.general.subcommands.HelpSubCommand;
import de.lucamaximal.homiecraft.commands.general.subcommands.ReloadSubCommand;
import de.lucamaximal.homiecraft.commands.general.subcommands.SubCommand;
import de.lucamaximal.homiecraft.commands.general.subcommands.SpawnSubCommand;
import de.lucamaximal.homiecraft.core.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class HomieCraftCommand implements CommandExecutor {

    private final Main plugin;
    private final List<SubCommand> subCommands = new ArrayList<>();

    public HomieCraftCommand(Main plugin) {
        this.plugin = plugin;

        // 🔥 Subcommands registrieren
        subCommands.add(new ReloadSubCommand(plugin));
        subCommands.add(new HelpSubCommand(plugin));
        subCommands.add(new SpawnSubCommand(plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            sender.sendMessage(plugin.getMessageManager().getMessage("help"));
            return true;
        }

        for (SubCommand sub : subCommands) {

            if (sub.getName().equalsIgnoreCase(args[0])) {
                sub.execute(sender, args);
                return true;
            }
        }

        // ❌ Unknown Subcommand
        sender.sendMessage(plugin.getMessageManager().getMessage("unknown_command"));
        return true;
    }
}
