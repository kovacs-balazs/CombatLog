package me.koba1.combatlog;

import me.koba1.combatlog.Events.JoinEvent;
import me.koba1.combatlog.Events.PvPEvent;
import me.koba1.combatlog.Events.QuitEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public final class Main extends JavaPlugin {


    public static ArrayList<Player> inPvp;
    @Override
    public void onEnable() {
        inPvp = new ArrayList<>();
        // Plugin startup logic
        PlayerData.setup();


        for(String key : PlayerData.get().getKeys(false)) {
            PlayerData.get().set(key, null);
        }
        PlayerData.save();

        getServer().getPluginManager().registerEvents(new PvPEvent(), this);
        getServer().getPluginManager().registerEvents(new QuitEvent(), this);
        getServer().getPluginManager().registerEvents(new JoinEvent(), this);

        if(!new File(getDataFolder() + "\\config.yml").exists()) saveResource("config.yml", false);
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        saveConfig();

        TimeManager.timer();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
