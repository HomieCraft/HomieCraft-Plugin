package de.lucamaximal.homiecraft.commands;

import de.lucamaximal.homiecraft.Main;

public class CommandManager {

    private final Main plugin;

    public CommandManager(Main plugin) {
        this.plugin = plugin;
    }

    public void registerCommands() {

        plugin.getCommand("homiecraft")
                .setExecutor(new HomieCraftCommand(plugin));

    }

}
