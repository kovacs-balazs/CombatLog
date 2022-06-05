package me.koba1.combatlog;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.*;
import java.util.UUID;

public class TimeManager {

    public static void startPvp(Player p) {
        PlayerData.get().set("Players." + p.getUniqueId(), (System.currentTimeMillis() / 1000));
        PlayerData.save();

        Main.inPvp.add(p);
    }

    public static void removePvp(Player p) {
        PlayerData.get().set("Players." + p.getUniqueId(), null);
        PlayerData.save();
        Main.inPvp.remove(p);
    }

    public static boolean inPvP(Player p) {
        return PlayerData.get().getString("Players." + p.getUniqueId()) != null;
    }

    private static Main m = Main.getPlugin(Main.class);

    public static void timer() {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    long unixTime = System.currentTimeMillis() / 1000;
                    PlayerData.reload();
                    for (String uuid : PlayerData.get().getConfigurationSection("Players").getKeys(false)) {
                        long pUnix = PlayerData.get().getLong("Players." + uuid);
                        int different = (int) (unixTime - pUnix);

                        Player p = Bukkit.getPlayer(UUID.fromString(uuid));
                        if (different >= m.getConfig().getInt("Timer")) {
                            removePvp(p);
                            Main.inPvp.remove(p);
                            p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(""));
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', m.getConfig().getString("no_longer_message")));
                            continue;
                        }

                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(
                                ChatColor.translateAlternateColorCodes('&',
                                        m.getConfig().getString("action_bar_message").replace("%time%", String.valueOf(m.getConfig().getInt("Timer") - different)))));
                    }
                } catch (NullPointerException ex) {
                }
            }
        }.runTaskTimer(m, 0L, 10L);
    }
}
