package de.lucamaximal.homiecraft.commands.tpa;

import de.lucamaximal.homiecraft.tpa.TpaManager;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class TpaHereCommand implements CommandExecutor {

    private final TpaManager manager;

    public TpaHereCommand(TpaManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)) return true;
        if (args.length != 1) return false;

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) return true;

        manager.sendHereRequest(player, target);
        return true;
    }
}
