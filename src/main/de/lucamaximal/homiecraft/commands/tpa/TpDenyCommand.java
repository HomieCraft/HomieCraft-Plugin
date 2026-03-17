package de.lucamaximal.homiecraft.commands.tpa;

import de.lucamaximal.homiecraft.tpa.TpaManager;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class TpDenyCommand implements CommandExecutor {

    private final TpaManager manager;

    public TpDenyCommand(TpaManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player player) {
            manager.deny(player);
        }

        return true;
    }
}
