package me.koba1.combatlog.Events;

import me.koba1.combatlog.Main;
import me.koba1.combatlog.TimeManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PvPEvent implements Listener {

    private Main m = Main.getPlugin(Main.class);

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if(e.getDamager() instanceof Player) {
            if(e.getEntity() instanceof  Player) {
                if(e.isCancelled()) return;
                Player damager = (Player) e.getDamager();
                Player p = (Player) e.getEntity();

                if(Main.inPvp.isEmpty()) {
                    damager.sendMessage(ChatColor.translateAlternateColorCodes('&', m.getConfig().getString("pvp_message")));
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', m.getConfig().getString("pvp_message")));

                    TimeManager.startPvp(damager);
                    TimeManager.startPvp(p);
                    return;
                }

                if(!Main.inPvp.contains(damager)) {
                    damager.sendMessage(ChatColor.translateAlternateColorCodes('&', m.getConfig().getString("pvp_message")));
                }

                if(!Main.inPvp.contains(p)) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', m.getConfig().getString("pvp_message")));
                }

                TimeManager.startPvp(damager);
                TimeManager.startPvp(p);
            }
        }
    }
}
