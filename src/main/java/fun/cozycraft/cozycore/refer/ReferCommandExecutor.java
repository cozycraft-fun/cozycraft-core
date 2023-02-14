package fun.cozycraft.cozycore.refer;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import fun.cozycraft.cozycore.api.API;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.IOException;
import java.util.logging.Logger;

public class ReferCommandExecutor implements CommandExecutor {

    private final Moshi moshi = new Moshi.Builder().build();
    private final JsonAdapter<ReferFriendResponsePayload> jsonAdapter = moshi.adapter(ReferFriendResponsePayload.class);

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        String username = args[0];
        String type = args.length > 1 ? args[1] : "java";
        String referredBy = "console";

        if (!type.equalsIgnoreCase("java") && !type.equalsIgnoreCase("bedrock")) {
            return false;
        }

        ReferFriendRequestPayload payload = new ReferFriendRequestPayload(username, type, referredBy);

        if (!(sender instanceof Player)) {
            API.referFriend(payload, new Callback() {

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    try (ResponseBody responseBody = response.body()) {
                        if (!response.isSuccessful()) {
                            sender.sendMessage("Failed to invite " + username);
                            throw new IOException("Failed to invite user: " + response);
                        }
                        TextComponent message = responseToMessage(responseBody, username);
                        sender.sendMessage(message);
                    }
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
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) {
                        sender.sendMessage("Failed to invite " + username);
                        throw new IOException("Failed to invite user: " + response);
                    }
                    TextComponent message = responseToMessage(responseBody, username);
                    sender.sendMessage(message);
                }
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                sender.sendMessage("Failed to invite " + username);
                e.printStackTrace();
            }
        });

        return true;
    }

    private TextComponent responseToMessage(ResponseBody responseBody, String username) throws IOException {
        ReferFriendResponsePayload data = jsonAdapter.fromJson(responseBody.source());
        Component url = Component.text("Click to copy the link.")
                .color(NamedTextColor.AQUA)
                .decorate(TextDecoration.UNDERLINED)
                .clickEvent(ClickEvent.copyToClipboard(data.url));

        return Component.text("Successfully invited ")
                .append(Component.text(username, NamedTextColor.AQUA))
                .append(Component.newline())
                .append(url);
    }
}
