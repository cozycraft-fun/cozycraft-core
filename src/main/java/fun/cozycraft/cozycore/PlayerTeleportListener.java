package fun.cozycraft.cozycore;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerTeleportListener implements Listener {
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        CozycoreState.lastPositions.put(event.getPlayer().getUniqueId(), event.getFrom());
    }
}
