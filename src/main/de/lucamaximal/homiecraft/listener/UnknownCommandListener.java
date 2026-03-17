package de.lucamaximal.homiecraft.listener;

import de.lucamaximal.homiecraft.core.Main;
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

        String command = message.split(" ")[0].substring(1);

        if (plugin.getServer().getPluginCommand(command) == null) {

            event.setCancelled(true);

            event.getPlayer().sendMessage(
                    plugin.getMessageManager().getMessage("unknown_command")
            );
        }
    }
}
