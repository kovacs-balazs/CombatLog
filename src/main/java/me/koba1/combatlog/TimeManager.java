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

        if(!Main.apis.containsKey(p)) {
            BarAPI api = new BarAPI(p, String.valueOf(m.getConfig().getInt("Timer")));
            api.addPlayer();
            Main.apis.put(p, api);
        } else {
            BarAPI api = Main.apis.get(p);
            api.edit(String.valueOf(m.getConfig().getInt("Timer")));
        }
    }

    public static void removePvp(Player p) {
        PlayerData.get().set("Players." + p.getUniqueId(), null);
        PlayerData.save();

        try {
            BarAPI api = Main.apis.get(p);
            api.removePlayer();
        } catch (NullPointerException ex) {
            Main.apis.remove(p);
        }
        Main.apis.remove(p);
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
                        BarAPI api = Main.apis.get(p);
                        if (different >= m.getConfig().getInt("Timer")) {
                            removePvp(p);
                            api.removePlayer();
                            Main.apis.remove(p);
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', m.getConfig().getString("no_longer_message")));
                            continue;
                        }

                        api.edit(String.valueOf(m.getConfig().getInt("Timer") - different));
//                        p.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(
//                                ChatColor.translateAlternateColorCodes('&',
//                                        m.getConfig().getString("action_bar_message").replace("%time%", String.valueOf(10 - different)))));
                    }
                } catch (NullPointerException ex) {
                }
            }
        }.runTaskTimer(m, 0L, 10L);
    }
}
