package fun.cozycraft.cozycore.api;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import fun.cozycraft.cozycore.CozycoreState;
import fun.cozycraft.cozycore.models.PlayerHome;
import fun.cozycraft.cozycore.api.home.PlayerHomesResponsePayload;
import fun.cozycraft.cozycore.api.refer.ReferFriendRequestPayload;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public class API {

    public static final String ENV_API_URL = "API_URL";
    private static final String DEFAULT_API_URL = "http://localhost:3000";
    private static final String API_KEY = System.getenv("COZYCRAFT_API_KEY");
    public static final OkHttpClient client = new OkHttpClient();
    private static final Moshi moshi = new Moshi.Builder().add(new PlayerHomeJSONAdapter()).build();
    private static final JsonAdapter<PlayerHomesResponsePayload> playerHomesJsonAdapter = moshi.adapter(PlayerHomesResponsePayload.class);

    public static void updatePlayerCount(int playerCount) throws Exception {
        RequestBody requestBody = new FormBody.Builder().add("playerCount", String.valueOf(playerCount)).build();
        Request request = buildRequest("/api/player-count").post(requestBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code: " + response);
                }
            }
        });
    }

    public static void referFriend(ReferFriendRequestPayload payload, Callback callback) {
        RequestBody requestBody = new FormBody.Builder()
                .add("username", payload.getUsername())
                .add("accountType", payload.getType())
                .add("referredBy", payload.getReferredBy())
                .build();
        Request request = buildRequest("/api/refer").post(requestBody).build();
        client.newCall(request).enqueue(callback);
    }

    public static void getHomesForPlayer(UUID playerId) {
        Request request = buildRequest("/api/homes?playerId=" + playerId.toString()).get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    PlayerHomesResponsePayload data = playerHomesJsonAdapter.fromJson(responseBody.source());
                    CozycoreState.homes.put(playerId, data.getHomes());
                }
            }
        });
    }

    public static void createPlayerHome(String playerId, PlayerHome home, Callback callback) {
        RequestBody requestBody = new FormBody.Builder()
                .add("id", home.getId().toString())
                .add("name", home.getName())
                .add("x", String.valueOf(home.getLocation().getX()))
                .add("z", String.valueOf(home.getLocation().getZ()))
                .add("y", String.valueOf(home.getLocation().getY()))
                .add("worldName", home.getLocation().getWorld().getName())
                .add("playerId", playerId)
                .build();
        Request request = buildRequest("/api/homes").post(requestBody).build();
        client.newCall(request).enqueue(callback);
    }

    public static void updatePlayerHome(String playerId, PlayerHome home, Callback callback) {
        RequestBody requestBody = new FormBody.Builder()
                .add("id", home.getId().toString())
                .add("name", home.getName())
                .add("x", String.valueOf(home.getLocation().getX()))
                .add("z", String.valueOf(home.getLocation().getZ()))
                .add("y", String.valueOf(home.getLocation().getY()))
                .add("worldName", home.getLocation().getWorld().getName())
                .add("playerId", playerId)
                .build();
        Request request = buildRequest("/api/homes").put(requestBody).build();
        client.newCall(request).enqueue(callback);
    }

    public static void deletePlayerHome(String playerId, String homeId, Callback callback) {
        RequestBody requestBody = new FormBody.Builder()
                .add("id", homeId)
                .add("playerId", playerId)
                .build();
        Request request = buildRequest("/api/homes").delete(requestBody).build();
        client.newCall(request).enqueue(callback);
    }

    public static Request.Builder buildRequest(String pathname) {
        return new Request.Builder()
                .header("Authorization", "Bearer " + API_KEY)
                .url(API_URL(pathname));
    }

    public static String API_URL(String pathname) {
        return (Optional.ofNullable(System.getenv(ENV_API_URL))
                .orElse(DEFAULT_API_URL)) + pathname;
    }
}
