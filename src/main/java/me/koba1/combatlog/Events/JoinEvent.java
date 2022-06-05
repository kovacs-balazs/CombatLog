package me.koba1.combatlog.Events;

import me.koba1.combatlog.Main;
import me.koba1.combatlog.TimeManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        TimeManager.removePvp(e.getPlayer());
        Main.inPvp.remove(e.getPlayer());
    }
}
