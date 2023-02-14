package fun.cozycraft.cozycore.home;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import fun.cozycraft.cozycore.CozycoreState;
import fun.cozycraft.cozycore.api.API;
import fun.cozycraft.cozycore.api.LocationJsonAdapter;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PlayerHomeUnsetCommandExecutor implements CommandExecutor {

    private final Moshi moshi = new Moshi.Builder().add(new LocationJsonAdapter()).build();
    private final JsonAdapter<PlayerHomeCreateResponsePayload> createPlayerHomeJsonAdapter = moshi.adapter(PlayerHomeCreateResponsePayload.class);

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

        API.deletePlayerHome(player.getUniqueId().toString(), home.get().getId().toString(), new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                player.sendMessage("Failed to delete home, please try again. If the problem persists contact the admin.");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    Optional<PlayerHomeCreateResponsePayload> data = Optional.ofNullable(createPlayerHomeJsonAdapter.fromJson(responseBody.source()));
                    if (!data.isPresent()) {
                        player.sendMessage("Failed to delete home, please try again. If the problem persists contact the admin.");
                        return;
                    }
                    playerHomes.remove(data.get().home.name);
                    CozycoreState.homes.put(player.getUniqueId(), playerHomes);
                    player.sendMessage("Home unset.");
                }
            }
        });

        return true;
    }
}
