package de.lucamaximal.homiecraft.commands.general.subcommands;

import de.lucamaximal.homiecraft.core.Main;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnSubCommand implements SubCommand {

    private final Main plugin;

    public SpawnSubCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "spawn";
    }

    @Override
    public String getPermission() {
        return "homiecraft.spawn";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players!");
            return;
        }

        if (!player.hasPermission(getPermission())) {
            player.sendMessage(plugin.getMessageManager().getMessage("no_permission"));
            return;
        }

        if (!plugin.getConfig().contains("spawn")) {
            player.sendMessage(plugin.getMessageManager().getMessage(player, "no_spawn_set"));
            return;
        }

        Location loc = new Location(
                plugin.getServer().getWorld(plugin.getConfig().getString("spawn.world")),
                plugin.getConfig().getDouble("spawn.x"),
                plugin.getConfig().getDouble("spawn.y"),
                plugin.getConfig().getDouble("spawn.z"),
                (float) plugin.getConfig().getDouble("spawn.yaw"),
                (float) plugin.getConfig().getDouble("spawn.pitch")
        );

        player.teleport(loc);
        player.sendMessage(plugin.getMessageManager().getMessage(player, "spawn_teleport"));
    }
}
