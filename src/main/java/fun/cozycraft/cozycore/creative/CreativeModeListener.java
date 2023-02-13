package fun.cozycraft.cozycore.creative;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import fun.cozycraft.cozycore.CozycoreState;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

public class CreativeModeListener implements Listener {

    @EventHandler
    public void onPlayerGameModeChange(PlayerGameModeChangeEvent event) {
        PlayerGameModeChangeEvent.Cause eventCause = event.getCause();

    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE) return;
        Block block = event.getBlockPlaced();
        Location location = block.getLocation();
        CozycoreState.creativeBlocks.add(location);
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE) return;
        event.getItemDrop().setCanPlayerPickup(false);
        event.getItemDrop().setCanMobPickup(false);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (!CozycoreState.creativeBlocks.contains(block.getLocation())) {
            return;
        }
        event.setDropItems(false);
        block.setType(Material.AIR);
    }

    @EventHandler
    public void onBlockDestroy(BlockDestroyEvent event) {
        Block block = event.getBlock();
        if (!CozycoreState.creativeBlocks.contains(block.getLocation())) {
            return;
        }
        block.setType(Material.AIR);
    }
}
