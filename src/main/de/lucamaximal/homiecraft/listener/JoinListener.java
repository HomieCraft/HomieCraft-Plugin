package de.lucamaximal.homiecraft.listener;

import de.lucamaximal.homiecraft.util.MessageUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        String message = MessageUtils.getMessage("messages.join")
                .replace("%player%", event.getPlayer().getName());

        event.getPlayer().sendMessage(message);

    }

}
