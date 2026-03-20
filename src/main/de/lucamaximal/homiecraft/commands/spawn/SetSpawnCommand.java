package de.lucamaximal.homiecraft.commands.spawn;

import de.lucamaximal.homiecraft.config.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SetSpawnCommand implements CommandExecutor {

    private final JavaPlugin plugin;
    private final MessageManager messageManager;

    public SetSpawnCommand(JavaPlugin plugin, MessageManager messageManager) {
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
        if (!player.hasPermission("homiecraft.setspawn")) {
            player.sendMessage(messageManager.getMessage("no_permission"));
            return true;
        }

        var loc = player.getLocation();

        plugin.getConfig().set("spawn.world", loc.getWorld().getName());
        plugin.getConfig().set("spawn.x", loc.getX());
        plugin.getConfig().set("spawn.y", loc.getY());
        plugin.getConfig().set("spawn.z", loc.getZ());
        plugin.getConfig().set("spawn.yaw", loc.getYaw());
        plugin.getConfig().set("spawn.pitch", loc.getPitch());

        plugin.saveConfig();

        player.sendMessage(messageManager.getMessage(player, "spawn_set"));

        return true;
    }
}
