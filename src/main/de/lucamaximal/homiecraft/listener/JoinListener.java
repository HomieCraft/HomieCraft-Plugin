package de.lucamaximal.homiecraft.listener;

import de.lucamaximal.homiecraft.util.MessageUtils;
import de.lucamaximal.homiecraft.util.Messages;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        event.getPlayer().sendMessage(
                MessageUtils.getMessage(Messages.JOIN_MESSAGE, event.getPlayer())
        );

    }

}
