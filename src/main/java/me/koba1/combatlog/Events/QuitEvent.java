package me.koba1.combatlog.Events;

import me.koba1.combatlog.Main;
import me.koba1.combatlog.PlayerData;
import me.koba1.combatlog.TimeManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if(TimeManager.inPvP(e.getPlayer())) {
            e.getPlayer().setHealth(0.0D);
        }
        TimeManager.removePvp(e.getPlayer());
        Main.apis.remove(e.getPlayer());
    }
}
