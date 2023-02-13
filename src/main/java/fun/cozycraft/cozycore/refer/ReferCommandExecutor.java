package fun.cozycraft.cozycore.refer;

import fun.cozycraft.cozycore.api.API;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class ReferCommandExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        String username = args[0];
        String type = args[1];
        String referredBy = "console";

        if (!type.equalsIgnoreCase("java") && !type.equalsIgnoreCase("bedrock")) {
            return false;
        }

        ReferFriendRequestPayload payload = new ReferFriendRequestPayload(username, type, referredBy);

        if (!(sender instanceof Player)) {
            API.referFriend(payload, new Callback() {

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        sender.sendMessage("Failed to invite " + username);
                        throw new IOException("Failed to invite user: " + response);
                    }
                    sender.sendMessage("Successfully invited " + username);
                }

                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    sender.sendMessage("Failed to invite " + username);
                    e.printStackTrace();
                }
            });
            return true;
        }

        Player player = (Player) sender;
        referredBy = player.getName();
        payload = new ReferFriendRequestPayload(username, type, referredBy);

        API.referFriend(payload, new Callback() {

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    sender.sendMessage("Failed to invite " + username);
                    throw new IOException("Failed to invite user: " + response);
                }
                sender.sendMessage("Successfully invited " + username);
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                sender.sendMessage("Failed to invite " + username);
                e.printStackTrace();
            }
        });

        return true;
    }
}
