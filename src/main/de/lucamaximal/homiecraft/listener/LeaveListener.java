package de.lucamaximal.homiecraft.listener;

import de.lucamaximal.homiecraft.config.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveListener implements Listener {

    private final MessageManager messageManager;

    public LeaveListener(MessageManager messageManager) {
        this.messageManager = messageManager;
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {

        event.quitMessage(null);

        Bukkit.broadcast(messageManager.getMessage(event.getPlayer(), "leave"));
    }
}
