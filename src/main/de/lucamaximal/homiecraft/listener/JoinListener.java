package de.lucamaximal.homiecraft.listener;

import de.lucamaximal.homiecraft.util.MessageUtils;
import de.lucamaximal.homiecraft.util.Messages;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        if (!event.getPlayer().hasPlayedBefore()) {
            // First Join Message
            MessageUtils.send(event.getPlayer(), Messages.FIRST_JOIN);
            return;
        }

        // Normal Join Message
        MessageUtils.send(event.getPlayer(), Messages.JOIN);
    }

    @EventHandler
    public void onQuit(org.bukkit.event.player.PlayerQuitEvent event) {

        // Leave Message
        MessageUtils.send(event.getPlayer(), Messages.LEAVE);
    }
}
