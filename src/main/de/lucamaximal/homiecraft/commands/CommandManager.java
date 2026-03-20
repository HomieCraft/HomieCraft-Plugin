package de.lucamaximal.homiecraft.commands;
 import de.lucamaximal.homiecraft.commands.general.HomieCraftCommand;
 import de.lucamaximal.homiecraft.commands.spawn.SetSpawnCommand;
 import de.lucamaximal.homiecraft.commands.spawn.SpawnCommand;
 import de.lucamaximal.homiecraft.commands.tpa.TpAcceptCommand;
 import de.lucamaximal.homiecraft.commands.tpa.TpDenyCommand;
 import de.lucamaximal.homiecraft.commands.tpa.TpaAllCommand;
 import de.lucamaximal.homiecraft.commands.tpa.TpaAutoCommand;
 import de.lucamaximal.homiecraft.commands.tpa.TpaCommand;
 import de.lucamaximal.homiecraft.commands.tpa.TpaHereAllCommand;
 import de.lucamaximal.homiecraft.commands.tpa.TpaHereCommand;
 import de.lucamaximal.homiecraft.commands.tpa.TpaToggleCommand;
 import de.lucamaximal.homiecraft.core.Main;
 import org.bukkit.command.CommandExecutor;
 import org.bukkit.plugin.PluginCommand;
 
 public class CommandManager {
 
     private final Main plugin;
 
     public CommandManager(Main plugin) {
         this.plugin = plugin;
     }
 
     public void registerCommands() {
         register("homiecraft", new HomieCraftCommand(plugin));
         register("spawn", new SpawnCommand(plugin, plugin.getMessageManager()));
         register("setspawn", new SetSpawnCommand(plugin, plugin.getMessageManager()));
         register("tpa", new TpaCommand(plugin));
         register("tpaccept", new TpAcceptCommand(plugin));
         register("tpdeny", new TpDenyCommand(plugin));
         register("tpahere", new TpaHereCommand(plugin));
         register("tpaall", new TpaAllCommand(plugin));
         register("tpahereall", new TpaHereAllCommand(plugin));
         register("tpaauto", new TpaAutoCommand(plugin));
         register("tpatoggle", new TpaToggleCommand(plugin));
     }
 
    private void register(String commandName, CommandExecutor executor) {
        PluginCommand command = plugin.getCommand(commandName);
        if (command == null) {
            plugin.getLogger().warning("Command '" + commandName + "' is missing in plugin.yml.");
            return;
        }
 
         command.setExecutor(executor);
     }
 }
