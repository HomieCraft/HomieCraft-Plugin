package de.lucamaximal.homiecraft.listener;

import de.lucamaximal.homiecraft.config.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private final MessageManager messageManager;

    public JoinListener(MessageManager messageManager) {
        this.messageManager = messageManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        // Vanilla Nachricht ausmachen
        event.joinMessage(null);

        if (!event.getPlayer().hasPlayedBefore()) {
            Bukkit.broadcast(messageManager.getMessage(event.getPlayer(), "first_join"));
            return;
        }

        Bukkit.broadcast(messageManager.getMessage(event.getPlayer(), "join"));
    }
}
