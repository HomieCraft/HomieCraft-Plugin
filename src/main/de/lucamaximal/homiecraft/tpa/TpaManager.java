package de.lucamaximal.homiecraft.tpa;

import de.lucamaximal.homiecraft.core.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.*;

public class TpaManager {

    private final Main plugin;

    private final Map<UUID, UUID> requests = new HashMap<>();
    private final Map<UUID, UUID> hereRequests = new HashMap<>();
    private final Map<UUID, Location> teleporting = new HashMap<>();

    private final Set<UUID> autoAccept = new HashSet<>();
    private final Set<UUID> toggleOff = new HashSet<>();

    public TpaManager(Main plugin) {
        this.plugin = plugin;
    }

    // =========================
    // TPA
    // =========================

    public void sendRequest(Player sender, Player target) {

        if (sender.equals(target)) {
            plugin.getMessageManager().send(sender, "tpa_self");
            return;
        }

        if (toggleOff.contains(target.getUniqueId())) {
            plugin.getMessageManager().send(sender, "tpa_blocked");
            return;
        }

        requests.put(target.getUniqueId(), sender.getUniqueId());

        plugin.getMessageManager().send(sender, "tpa_sent", target);
        plugin.getMessageManager().send(target, "tpa_received", sender);

        if (autoAccept.contains(target.getUniqueId())) {
            accept(target);
            return;
        }

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (requests.containsKey(target.getUniqueId())) {
                requests.remove(target.getUniqueId());
                plugin.getMessageManager().send(target, "tpa_expired");
            }
        }, 20 * 60);
    }

    public void accept(Player target) {

        UUID senderUUID = requests.remove(target.getUniqueId());

        if (senderUUID == null) {
            plugin.getMessageManager().send(target, "tpa_none");
            return;
        }

        Player sender = Bukkit.getPlayer(senderUUID);
        if (sender == null) return;

        startTeleport(sender, target.getLocation());

        plugin.getMessageManager().send(target, "tpa_accept");
        plugin.getMessageManager().send(sender, "tpa_accepted");
    }

    public void deny(Player target) {

        UUID senderUUID = requests.remove(target.getUniqueId());

        if (senderUUID == null) {
            plugin.getMessageManager().send(target, "tpa_none");
            return;
        }

        Player sender = Bukkit.getPlayer(senderUUID);

        plugin.getMessageManager().send(target, "tpa_deny");

        if (sender != null) {
            plugin.getMessageManager().send(sender, "tpa_denied");
        }
    }

    // =========================
    // TPA HERE
    // =========================

    public void sendHereRequest(Player sender, Player target) {

        if (toggleOff.contains(target.getUniqueId())) {
            plugin.getMessageManager().send(sender, "tpa_blocked");
            return;
        }

        hereRequests.put(target.getUniqueId(), sender.getUniqueId());

        plugin.getMessageManager().send(sender, "tpahere_sent", target);
        plugin.getMessageManager().send(target, "tpahere_received", sender);

        if (autoAccept.contains(target.getUniqueId())) {
            acceptHere(target);
        }
    }

    public void acceptHere(Player target) {

        UUID senderUUID = hereRequests.remove(target.getUniqueId());
        if (senderUUID == null) return;

        Player sender = Bukkit.getPlayer(senderUUID);
        if (sender == null) return;

        startTeleport(target, sender.getLocation());
    }

    // =========================
    // ALL
    // =========================

    public void sendAll(Player sender) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!p.equals(sender)) sendRequest(sender, p);
        }
    }

    public void sendHereAll(Player sender) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!p.equals(sender)) sendHereRequest(sender, p);
        }
    }

    // =========================
    // TOGGLE
    // =========================

    public void toggleAuto(Player player) {
        if (autoAccept.contains(player.getUniqueId())) {
            autoAccept.remove(player.getUniqueId());
            plugin.getMessageManager().send(player, "tpa_auto_off");
        } else {
            autoAccept.add(player.getUniqueId());
            plugin.getMessageManager().send(player, "tpa_auto_on");
        }
    }

    public void toggleBlock(Player player) {
        if (toggleOff.contains(player.getUniqueId())) {
            toggleOff.remove(player.getUniqueId());
            plugin.getMessageManager().send(player, "tpa_toggle_on");
        } else {
            toggleOff.add(player.getUniqueId());
            plugin.getMessageManager().send(player, "tpa_toggle_off");
        }
    }

    public boolean isAuto(Player player) {
        return autoAccept.contains(player.getUniqueId());
    }

    public boolean isBlocked(Player player) {
        return toggleOff.contains(player.getUniqueId());
    }

    // =========================
    // TELEPORT
    // =========================

    private void startTeleport(Player player, Location loc) {

        teleporting.put(player.getUniqueId(), player.getLocation());

        new BukkitRunnable() {

            int seconds = 5;

            @Override
            public void run() {

                if (!teleporting.containsKey(player.getUniqueId())) {
                    cancel();
                    return;
                }

                if (seconds == 0) {
                    teleporting.remove(player.getUniqueId());
                    player.teleport(loc);
                    plugin.getMessageManager().send(player, "tpa_teleport");
                    cancel();
                    return;
                }

                player.sendActionBar(Component.text("Teleport in " + seconds + "..."));
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);

                seconds--;
            }
        }.runTaskTimer(plugin, 0, 20);
    }

    public void cancelTeleport(Player player) {
        if (teleporting.remove(player.getUniqueId()) != null) {
            plugin.getMessageManager().send(player, "tpa_cancel_move");
        }
    }
}
