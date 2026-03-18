package de.lucamaximal.homiecraft.listener;

import de.lucamaximal.homiecraft.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class UnknownCommandListener implements Listener {

    private final Main plugin;

    public UnknownCommandListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {

        String message = event.getMessage();

        // "/" entfernen
        String command = message.split(" ")[0].substring(1).toLowerCase();

        // Prüfen ob Command existiert
        if (Bukkit.getCommandMap().getCommand(command) != null) {
            return; // existiert -> nichts machen
        }

        // ❌ Command existiert nicht
        event.setCancelled(true);

        plugin.getMessageManager().send(event.getPlayer(), "unknown_command");
    }
}
