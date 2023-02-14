package fun.cozycraft.cozycore;

import fun.cozycraft.cozycore.api.API;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerNetworkListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        API.getHomesForPlayer(player.getUniqueId().toString());
//        int playerCount = Bukkit.getOnlinePlayers().size();
//        try {
//            API.updatePlayerCount(playerCount);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent _event) {
//        int playerCount = Bukkit.getOnlinePlayers().size();
//        try {
//            API.updatePlayerCount(playerCount);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

}
