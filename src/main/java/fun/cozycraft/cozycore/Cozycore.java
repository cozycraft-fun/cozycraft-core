package fun.cozycraft.cozycore;

import fun.cozycraft.cozycore.api.API;
import fun.cozycraft.cozycore.back.PlayerBackCommandExecutor;
import fun.cozycraft.cozycore.home.PlayerHome;
import fun.cozycraft.cozycore.home.PlayerHomeCommandExecutor;
import fun.cozycraft.cozycore.home.PlayerHomeSetCommandExecutor;
import fun.cozycraft.cozycore.refer.ReferCommandExecutor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class Cozycore extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(this, this);
        Objects.requireNonNull(this.getCommand("home")).setExecutor(new PlayerHomeCommandExecutor());
        Objects.requireNonNull(this.getCommand("homeset")).setExecutor(new PlayerHomeSetCommandExecutor());
        Objects.requireNonNull(this.getCommand("back")).setExecutor(new PlayerBackCommandExecutor());
        Objects.requireNonNull(this.getCommand("refer")).setExecutor(new ReferCommandExecutor());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent _event) {
        int playerCount = Bukkit.getOnlinePlayers().size();
        try {
            API.updatePlayerCount(playerCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent _event) {
        int playerCount = Bukkit.getOnlinePlayers().size();
        try {
            API.updatePlayerCount(playerCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
