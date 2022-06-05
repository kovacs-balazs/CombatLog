package me.koba1.combatlog;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class BarAPI {

    Player p;
    String time;
    BossBar bar;
    private static Main m = Main.getPlugin(Main.class);

    public BarAPI(Player p) {
        this.p = p;
    }

    public BarAPI(Player p, String time) {
        this.p = p;
        this.time = time;
    }

    public void edit(String time) {
        this.bar.setTitle(
                ChatColor.translateAlternateColorCodes('&', m.getConfig().getString("bossbar_message").replace("%time%", time)));
    }

    public void addPlayer() {
        if(this.bar == null) {
            this.bar = Bukkit.createBossBar(
                    ChatColor.translateAlternateColorCodes('&', m.getConfig().getString("bossbar_message").replace("%time%", this.time)), BarColor.RED, BarStyle.SOLID);
            this.bar.setVisible(true);
            this.bar.addPlayer(this.p);
        } else {
            this.bar.setTitle(
                    ChatColor.translateAlternateColorCodes('&', m.getConfig().getString("bossbar_message").replace("%time%", time)));
        }
    }

    public void removePlayer() {
        try {
            this.bar.removePlayer(this.p);
            Main.apis.remove(p);
        } catch (NullPointerException ex) {
            Main.apis.remove(p);
        }
    }
}
