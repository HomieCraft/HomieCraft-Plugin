package de.lucamaximal.homiecraft.manager;

import de.lucamaximal.homiecraft.Main;

public class MessageManager {

    private final Main plugin;

    public MessageManager(Main plugin) {
        this.plugin = plugin;
    }

    public String getMessage(String path) {
        return plugin.getConfig().getString(path);
    }

}
