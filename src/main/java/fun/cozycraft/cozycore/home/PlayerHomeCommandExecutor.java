package fun.cozycraft.cozycore.home;

import fun.cozycraft.cozycore.CozycoreState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Optional;

public class PlayerHomeCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("/home may only be used by players.");
            return true;
        }
        Player player = (Player) sender;
        Optional<String> homeHumanId = Optional.of(args[0]);
        Optional<PlayerHome> home = Arrays.stream(CozycoreState.homes.get(player.getUniqueId())).filter(playerHome -> playerHome.name.equalsIgnoreCase(homeHumanId.get())).findFirst();

        if (home.isPresent()) {
            player.teleport(home.get().getLocation());
            return true;
        }
        home = Arrays.stream(CozycoreState.homes.get(player.getUniqueId())).findFirst();
        if (!home.isPresent()) {
            sender.sendMessage("You do not have a house set. To set a house use /sethome <?name>");
            return true;
        }
        player.teleport(home.get().getLocation());
        return true;
    }
}
