package de.lucamaximal.homiecraft.util;

import de.lucamaximal.homiecraft.core.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class MessageManager {

    private final Main plugin;
    private final PlaceholderManager placeholderManager;
    private final MiniMessage miniMessage;

    public MessageManager(Main plugin) {
        this.plugin = plugin;
        this.placeholderManager = new PlaceholderManager(plugin);
        this.miniMessage = MiniMessage.miniMessage();
    }

    public Component get(Player player, String key, Player target) {

        String raw = plugin.getConfig().getString("messages." + key);

        if (raw == null) {
            return Component.text("Missing message: " + key);
        }

        String parsed = placeholderManager.parse(player, raw, target);

        return miniMessage.deserialize(parsed);
    }

    public void send(Player player, String key) {
        player.sendMessage(get(player, key, null));
    }

    public void send(Player player, String key, Player target) {
        player.sendMessage(get(player, key, target));
    }
}
