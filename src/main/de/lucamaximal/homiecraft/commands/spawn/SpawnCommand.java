package de.lucamaximal.homiecraft.commands.spawn;

import de.lucamaximal.homiecraft.config.MessageManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SpawnCommand implements CommandExecutor {

    private final JavaPlugin plugin;
    private final MessageManager messageManager;

    public SpawnCommand(JavaPlugin plugin, MessageManager messageManager) {
        this.plugin = plugin;
        this.messageManager = messageManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players!");
            return true;
        }

        // 🔐 Permission Check
        if (!player.hasPermission("homiecraft.spawn")) {
            player.sendMessage(messageManager.getMessage("no_permission"));
            return true;
        }

        if (!plugin.getConfig().contains("spawn")) {
            player.sendMessage(messageManager.getMessage(player, "no_spawn_set"));
            return true;
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
        player.sendMessage(messageManager.getMessage(player, "spawn_teleport"));

        return true;
    }
}
