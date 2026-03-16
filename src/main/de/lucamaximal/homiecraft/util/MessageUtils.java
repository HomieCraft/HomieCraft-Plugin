package de.lucamaximal.homiecraft.util;

import de.lucamaximal.homiecraft.Main;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageUtils {

    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    public static Component get(String path, Player player) {

        String prefix = Main.getInstance().getConfig().getString(Messages.PREFIX);
        String message = Main.getInstance().getConfig().getString(path);

        if(prefix == null) prefix = "";
        if(message == null) message = "";

        String finalMessage = prefix + message;

        if(player != null &&
                Main.getInstance().getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {

            finalMessage = PlaceholderAPI.setPlaceholders(player, finalMessage);
        }

        return miniMessage.deserialize(finalMessage);
    }

    public static void send(CommandSender sender, String path) {

        if(sender instanceof Player player) {

            player.sendMessage(get(path, player));

        } else {

            String prefix = Main.getInstance().getConfig().getString(Messages.PREFIX);
            String message = Main.getInstance().getConfig().getString(path);

            if(prefix == null) prefix = "";
            if(message == null) message = "";

            sender.sendMessage(
                    miniMessage.deserialize(prefix + message)
            );

        }

    }

}
