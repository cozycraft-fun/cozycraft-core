package fun.cozycraft.cozycore.home;

import fun.cozycraft.cozycore.CozycoreState;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerHomeSetCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("/homeset may only be used by players.");
            return true;
        }
        String homeName = args.length > 0 ? args[0] : "home";
        Player player = (Player) sender;
        Location location = player.getLocation();
        Map<String, PlayerHome> playerHomes = CozycoreState.homes.getOrDefault(player.getUniqueId(), new HashMap<>());

        playerHomes.put(
                homeName,
                new PlayerHome(UUID.randomUUID(), homeName, location)
        );
        CozycoreState.homes.put(player.getUniqueId(), playerHomes);
        player.sendMessage("Home set.");

        return true;
    }
}
