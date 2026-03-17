package de.lucamaximal.homiecraft.tpa;

import de.lucamaximal.homiecraft.core.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.*;

public class TpaManager {

    private final Main plugin;

    // target -> sender
    private final Map<UUID, UUID> requests = new HashMap<>();
    private final Map<UUID, UUID> hereRequests = new HashMap<>();

    private final Map<UUID, Location> teleporting = new HashMap<>();

    private final Set<UUID> autoAccept = new HashSet<>();
    private final Set<UUID> toggleOff = new HashSet<>();

    public TpaManager(Main plugin) {
        this.plugin = plugin;
    }

    // =========================
    // NORMAL TPA
    // =========================

    public void sendRequest(Player sender, Player target) {

        if (sender.equals(target)) {
            sender.sendMessage(plugin.getMessageManager().getMessage("tpa_self"));
            return;
        }

        if (toggleOff.contains(target.getUniqueId())) {
            sender.sendMessage(plugin.getMessageManager().getMessage("tpa_blocked"));
            return;
        }

        requests.put(target.getUniqueId(), sender.getUniqueId());

        sender.sendMessage(plugin.getMessageManager().getMessage(sender, "tpa_sent")
                .replaceText(b -> b.matchLiteral("%target%").replacement(target.getName())));

        target.sendMessage(plugin.getMessageManager().getMessage(target, "tpa_received")
                .replaceText(b -> b.matchLiteral("%player%").replacement(sender.getName())));

        playSound(sender, "tpa_send");
        playSound(target, "tpa_receive");

        if (autoAccept.contains(target.getUniqueId())) {
            accept(target);
            return;
        }

        // Timeout
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (requests.containsKey(target.getUniqueId())) {
                requests.remove(target.getUniqueId());
                target.sendMessage(plugin.getMessageManager().getMessage("tpa_expired"));
            }
        }, 20 * 60);
    }

    public void accept(Player target) {

        UUID senderUUID = requests.remove(target.getUniqueId());

        if (senderUUID == null) {
            target.sendMessage(plugin.getMessageManager().getMessage("tpa_none"));
            return;
        }

        Player sender = Bukkit.getPlayer(senderUUID);
        if (sender == null) {
            target.sendMessage(plugin.getMessageManager().getMessage("tpa_offline"));
            return;
        }

        startTeleport(sender, target.getLocation());

        target.sendMessage(plugin.getMessageManager().getMessage("tpa_accept"));
        sender.sendMessage(plugin.getMessageManager().getMessage("tpa_accepted"));

        playSound(sender, "tpa_accept");
        playSound(target, "tpa_accept");
    }

    public void deny(Player target) {

        UUID senderUUID = requests.remove(target.getUniqueId());

        if (senderUUID == null) {
            target.sendMessage(plugin.getMessageManager().getMessage("tpa_none"));
            return;
        }

        Player sender = Bukkit.getPlayer(senderUUID);

        target.sendMessage(plugin.getMessageManager().getMessage("tpa_deny"));

        if (sender != null) {
            sender.sendMessage(plugin.getMessageManager().getMessage("tpa_denied"));
            playSound(sender, "tpa_deny");
        }

        playSound(target, "tpa_deny");
    }

    // =========================
    // TPA HERE
    // =========================

    public void sendHereRequest(Player sender, Player target) {

        if (toggleOff.contains(target.getUniqueId())) {
            sender.sendMessage(plugin.getMessageManager().getMessage("tpa_blocked"));
            return;
        }

        hereRequests.put(target.getUniqueId(), sender.getUniqueId());

        sender.sendMessage(plugin.getMessageManager().getMessage(sender, "tpahere_sent")
                .replaceText(b -> b.matchLiteral("%target%").replacement(target.getName())));

        target.sendMessage(plugin.getMessageManager().getMessage(target, "tpahere_received")
                .replaceText(b -> b.matchLiteral("%player%").replacement(sender.getName())));

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
        for (Player target : Bukkit.getOnlinePlayers()) {
            if (!target.equals(sender)) {
                sendRequest(sender, target);
            }
        }
    }

    public void sendHereAll(Player sender) {
        for (Player target : Bukkit.getOnlinePlayers()) {
            if (!target.equals(sender)) {
                sendHereRequest(sender, target);
            }
        }
    }

    // =========================
    // TOGGLES
    // =========================

    public void toggleAuto(Player player) {
        if (autoAccept.contains(player.getUniqueId())) {
            autoAccept.remove(player.getUniqueId());
            player.sendMessage(plugin.getMessageManager().getMessage("tpa_auto_off"));
        } else {
            autoAccept.add(player.getUniqueId());
            player.sendMessage(plugin.getMessageManager().getMessage("tpa_auto_on"));
        }
    }

    public void toggleBlock(Player player) {
        if (toggleOff.contains(player.getUniqueId())) {
            toggleOff.remove(player.getUniqueId());
            player.sendMessage(plugin.getMessageManager().getMessage("tpa_toggle_on"));
        } else {
            toggleOff.add(player.getUniqueId());
            player.sendMessage(plugin.getMessageManager().getMessage("tpa_toggle_off"));
        }
    }

    // =========================
    // TELEPORT SYSTEM
    // =========================

    private void startTeleport(Player player, Location target) {

        teleporting.put(player.getUniqueId(), player.getLocation());

        new org.bukkit.scheduler.BukkitRunnable() {

            int seconds = 5;

            @Override
            public void run() {

                if (!teleporting.containsKey(player.getUniqueId())) {
                    cancel();
                    return;
                }

                if (seconds == 0) {
                    teleporting.remove(player.getUniqueId());
                    player.teleport(target);
                    player.sendMessage(plugin.getMessageManager().getMessage("tpa_teleport"));
                    cancel();
                    return;
                }

                player.sendActionBar(Component.text("Teleport in " + seconds + "..."));
                playSound(player, "tpa_countdown");

                seconds--;
            }
        }.runTaskTimer(plugin, 0, 20);
    }

    public void cancelTeleport(Player player) {
        if (teleporting.containsKey(player.getUniqueId())) {
            teleporting.remove(player.getUniqueId());
            player.sendMessage(plugin.getMessageManager().getMessage("tpa_cancel_move"));
            playSound(player, "tpa_deny");
        }
    }

    // =========================
    // SOUND
    // =========================

    private void playSound(Player player, String key) {
        String soundName = plugin.getConfig().getString("sounds." + key);
        if (soundName == null) return;

        try {
            Sound sound = Sound.valueOf(soundName);
            player.playSound(player.getLocation(), sound, 1f, 1f);
        } catch (Exception ignored) {}
    }
}
