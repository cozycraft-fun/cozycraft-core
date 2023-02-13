package fun.cozycraft.cozycore.back;

import fun.cozycraft.cozycore.CozycoreState;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PlayerBackCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;
        Optional<Location> previousLocation = Optional.ofNullable(CozycoreState.lastPositions.get(player.getUniqueId()));

        if (!previousLocation.isPresent()) {
            sender.sendMessage("You do not have a previous location to return to.");
            return true;
        }

        player.teleport(previousLocation.get());

        return true;
    }
}
