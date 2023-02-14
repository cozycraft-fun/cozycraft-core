package fun.cozycraft.cozycore.home;

import fun.cozycraft.cozycore.CozycoreState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

public class PlayerHomesCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        UUID uuid = player.getUniqueId();
        Map<String, PlayerHome> homes = CozycoreState.homes.get(uuid);
        player.sendMessage("Homes: " + String.join(", ", homes.keySet()));
        return true;
    }
}
