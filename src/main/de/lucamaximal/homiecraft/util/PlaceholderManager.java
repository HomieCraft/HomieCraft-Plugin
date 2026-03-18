package de.lucamaximal.homiecraft.util;

import de.lucamaximal.homiecraft.core.Main;
import me.clip.placeholderapi.PlaceholderAPI;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.entity.Player;

public class PlaceholderManager {

    private final Main plugin;
    private LuckPerms luckPerms;

    public PlaceholderManager(Main plugin) {
        this.plugin = plugin;

        try {
            this.luckPerms = LuckPermsProvider.get();
        } catch (Exception ignored) {
            this.luckPerms = null;
        }
    }

    public String parse(Player player, String message, Player target) {

        if (message == null) return "";

        // =========================
        // BASIC PLACEHOLDERS
        // =========================

        if (player != null) {
            message = message.replace("%player_name%", player.getName());
            message = message.replace("%player%", getPrefix(player) + player.getName());
            message = message.replace("%rank%", getRank(player));
            message = message.replace("%tpa_auto%", getTpaAuto(player));
            message = message.replace("%tpa_toggle%", getTpaToggle(player));
        }

        if (target != null) {
            message = message.replace("%target_name%", target.getName());
            message = message.replace("%target%", getPrefix(target) + target.getName());
        }

        // =========================
        // PLACEHOLDERAPI SUPPORT
        // =========================

        if (plugin.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI") && player != null) {
            message = PlaceholderAPI.setPlaceholders(player, message);
        }

        return message;
    }

    // =========================
    // RANK (LuckPerms)
    // =========================

    public String getRank(Player player) {

        if (luckPerms == null) return "Spieler";

        User user = luckPerms.getUserManager().getUser(player.getUniqueId());
        if (user == null) return "Spieler";

        return user.getPrimaryGroup();
    }

    // =========================
    // PREFIX (LuckPerms)
    // =========================

    public String getPrefix(Player player) {

        if (luckPerms == null) return "";

        User user = luckPerms.getUserManager().getUser(player.getUniqueId());
        if (user == null) return "";

        String prefix = user.getCachedData().getMetaData().getPrefix();
        if (prefix == null) return "";

        return convertLegacy(prefix) + " ";
    }

    // =========================
    // TPA STATUS
    // =========================

    private String getTpaAuto(Player player) {
        if (plugin.getTpaManager().isAuto(player)) {
            return "AN";
        } else {
            return "AUS";
        }
    }

    private String getTpaToggle(Player player) {
        if (plugin.getTpaManager().isBlocked(player)) {
            return "BLOCKIERT";
        } else {
            return "AKTIV";
        }
    }

    // =========================
    // COLOR CONVERTER (& → MiniMessage)
    // =========================

    private String convertLegacy(String text) {

        return text
                .replace("&0", "<black>")
                .replace("&1", "<dark_blue>")
                .replace("&2", "<dark_green>")
                .replace("&3", "<dark_aqua>")
                .replace("&4", "<dark_red>")
                .replace("&5", "<dark_purple>")
                .replace("&6", "<gold>")
                .replace("&7", "<gray>")
                .replace("&8", "<dark_gray>")
                .replace("&9", "<blue>")
                .replace("&a", "<green>")
                .replace("&b", "<aqua>")
                .replace("&c", "<red>")
                .replace("&d", "<light_purple>")
                .replace("&e", "<yellow>")
                .replace("&f", "<white>")
                .replace("&l", "<bold>")
                .replace("&o", "<italic>")
                .replace("&n", "<underlined>")
                .replace("&m", "<strikethrough>")
                .replace("&r", "<reset>");
    }
}
