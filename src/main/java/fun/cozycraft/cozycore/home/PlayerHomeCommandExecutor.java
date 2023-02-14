package fun.cozycraft.cozycore.home;

import fun.cozycraft.cozycore.CozycoreState;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PlayerHomeCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("/home may only be used by players.");
            return true;
        }

        Player player = (Player) sender;
        String homeName = args.length > 0 ? args[0] : "home";
        Map<String, PlayerHome> playerHomes = CozycoreState.homes.getOrDefault(player.getUniqueId(), new HashMap<>());
        Optional<PlayerHome> home = Optional.ofNullable(playerHomes.get(homeName));

        if (!home.isPresent()) {
            sender.sendMessage("You do not have a home set. To set a home use /homeset [name]");
            return true;
        }

        Bukkit.getLogger().info(home.get().location != null ? home.get().location.toString() : "is nulled...");
        Bukkit.getLogger().info(home.get().location.toString());

        player.teleport(home.get().getLocation());
        return true;
    }
}
