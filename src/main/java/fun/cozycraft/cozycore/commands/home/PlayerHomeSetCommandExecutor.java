package fun.cozycraft.cozycore.commands.home;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import fun.cozycraft.cozycore.CozycoreState;
import fun.cozycraft.cozycore.api.API;
import fun.cozycraft.cozycore.api.PlayerHomeJSONAdapter;
import fun.cozycraft.cozycore.models.PlayerHome;
import fun.cozycraft.cozycore.api.home.PlayerHomeCreateResponsePayload;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerHomeSetCommandExecutor implements CommandExecutor {

    private final Moshi moshi = new Moshi.Builder().add(new PlayerHomeJSONAdapter()).build();
    private final JsonAdapter<PlayerHomeCreateResponsePayload> createPlayerHomeJsonAdapter = moshi.adapter(PlayerHomeCreateResponsePayload.class);

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
        PlayerHome newHome = new PlayerHome(UUID.randomUUID().toString(), homeName, location);
        PlayerHome existingHome = playerHomes.get(homeName);

        if (existingHome != null) {
            newHome = new PlayerHome(existingHome.getId(), existingHome.getName(), location);
            API.updatePlayerHome(player.getUniqueId().toString(), newHome, new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    e.printStackTrace();
                    player.sendMessage("Failed to set new home, please try again. If the problem persists contact the admin.");
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    try (ResponseBody responseBody = response.body()) {
                        PlayerHomeCreateResponsePayload data = createPlayerHomeJsonAdapter.fromJson(responseBody.source());
                        playerHomes.put(homeName, data.getHome());
                        CozycoreState.homes.put(player.getUniqueId(), playerHomes);
                        player.sendMessage("Home updated.");
                    }
                }
            });
            return true;
        }

        API.createPlayerHome(player.getUniqueId().toString(), newHome, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                player.sendMessage("Failed to set new home, please try again. If the problem persists contact the admin.");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    PlayerHomeCreateResponsePayload data = createPlayerHomeJsonAdapter.fromJson(responseBody.source());
                    playerHomes.put(homeName, data.getHome());
                    CozycoreState.homes.put(player.getUniqueId(), playerHomes);
                    player.sendMessage("Home set.");
                }
            }
        });

        return true;
    }
}
