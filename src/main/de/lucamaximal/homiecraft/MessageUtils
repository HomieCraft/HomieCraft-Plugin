package de.lucamaximal.homiecraft.util;

import de.lucamaximal.homiecraft.Main;
import org.bukkit.ChatColor;

public class MessageUtils {

    public static String getMessage(String path) {

        String prefix = Main.getInstance().getConfig().getString("messages.prefix");
        String message = Main.getInstance().getConfig().getString(path);

        return ChatColor.translateAlternateColorCodes('&', prefix + message);
    }

}
