package fun.cozycraft.cozycore.home;

import fun.cozycraft.cozycore.CozycoreState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PlayerHomeUnsetCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("/homeunset may only be used by players.");
            return true;
        }

        String homeName = args.length > 0 ? args[0] : "home";
        Player player = (Player) sender;
        Map<String, PlayerHome> playerHomes = CozycoreState.homes.getOrDefault(player.getUniqueId(), new HashMap<>());
        Optional<PlayerHome> home = Optional.ofNullable(playerHomes.get(homeName));
        if (!home.isPresent()) {
            player.sendMessage("Home does not exist.");
            return true;
        }

        playerHomes.remove(homeName);
        CozycoreState.homes.put(player.getUniqueId(), playerHomes);
        player.sendMessage("Home unset.");
        return true;
    }
}
