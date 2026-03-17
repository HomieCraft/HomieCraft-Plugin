package de.lucamaximal.homiecraft.config;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Map;

public class MessageManager {

    private final FileConfiguration config;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    public MessageManager(FileConfiguration config) {
        this.config = config;
    }

    public Component getMessage(Player player, String key) {
        return getMessage(player, key, null);
    }

    public Component getMessage(Player player, String key, Map<String, String> placeholders) {
        String msg = config.getString("messages." + key);

        if (msg == null) {
            return Component.text("Message not found: " + key);
        }

        String prefix = config.getString("messages.prefix", "");
        msg = prefix + msg;

        // Eigene Placeholder
        if (placeholders != null) {
            for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                msg = msg.replace("%" + entry.getKey() + "%", entry.getValue());
            }
        }

        // Standard Placeholder
        if (player != null) {
            msg = msg.replace("%player%", player.getName());
        }

        // PlaceholderAPI
        if (player != null && Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            msg = PlaceholderAPI.setPlaceholders(player, msg);
        }

        return miniMessage.deserialize(msg);
    }

    // Für Konsole (kein Player!)
    public Component getMessage(String key) {
        String msg = config.getString("messages." + key);

        if (msg == null) {
            return Component.text("Message not found: " + key);
        }

        String prefix = config.getString("messages.prefix", "");
        msg = prefix + msg;

        return miniMessage.deserialize(msg);
    }
}
