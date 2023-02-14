package fun.cozycraft.cozycore;

import fun.cozycraft.cozycore.back.PlayerBackCommandExecutor;
import fun.cozycraft.cozycore.creative.CreativeModeListener;
import fun.cozycraft.cozycore.home.PlayerHomeCommandExecutor;
import fun.cozycraft.cozycore.home.PlayerHomesCommandExecutor;
import fun.cozycraft.cozycore.home.PlayerHomeSetCommandExecutor;
import fun.cozycraft.cozycore.home.PlayerHomeUnsetCommandExecutor;
import fun.cozycraft.cozycore.refer.ReferCommandExecutor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Cozycore extends JavaPlugin {

    @Override
    public void onEnable() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new CreativeModeListener(), this);
        pm.registerEvents(new PlayerTeleportListener(), this);
        pm.registerEvents(new PlayerNetworkListener(), this);
//        pm.registerEvents(new PlayerHomeListener(), this);

        Objects.requireNonNull(this.getCommand("home")).setExecutor(new PlayerHomeCommandExecutor());
        Objects.requireNonNull(this.getCommand("homes")).setExecutor(new PlayerHomesCommandExecutor());
        Objects.requireNonNull(this.getCommand("homeset")).setExecutor(new PlayerHomeSetCommandExecutor());
        Objects.requireNonNull(this.getCommand("homeunset")).setExecutor(new PlayerHomeUnsetCommandExecutor());
        Objects.requireNonNull(this.getCommand("back")).setExecutor(new PlayerBackCommandExecutor());
        Objects.requireNonNull(this.getCommand("refer")).setExecutor(new ReferCommandExecutor());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
