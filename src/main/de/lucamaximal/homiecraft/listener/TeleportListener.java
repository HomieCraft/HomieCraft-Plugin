package de.lucamaximal.homiecraft.listener;

import de.lucamaximal.homiecraft.tpa.TpaManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class TeleportListener implements Listener {

    private final TpaManager tpaManager;

    public TeleportListener(TpaManager tpaManager) {
        this.tpaManager = tpaManager;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {

        if (event.getFrom().distance(event.getTo()) > 0) {
            tpaManager.cancelTeleport(event.getPlayer());
        }
    }
}
