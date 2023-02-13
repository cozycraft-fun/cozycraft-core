package fun.cozycraft.cozycore.api;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import fun.cozycraft.cozycore.refer.ReferFriendRequestPayload;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Optional;

public class API {

    public static final String ENV_API_URL = "API_URL";
    private static final String DEFAULT_API_URL = "http://localhost:3000";
    private static final String API_KEY = System.getenv("COZYCRAFT_API_KEY");
    public static final OkHttpClient client = new OkHttpClient();
    public static final Moshi moshi = new Moshi.Builder().build();

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
                .add("type", payload.getType())
                .add("referredBy", payload.getReferredBy())
                .build();
        Request request = buildRequest("/api/refer").post(requestBody).build();
        client.newCall(request).enqueue(callback);
    }

    public static void getHomesForPlayer(String playerId) throws Exception {}

    public static Request.Builder buildRequest(String pathname) {
        return new Request.Builder()
                .header("Authorization", "Bearer " + API_KEY)
                .url(API_URL(pathname));
    }

    public static String API_URL(String pathname) {
        return Optional.of(System.getenv(ENV_API_URL) + pathname)
                .orElse(DEFAULT_API_URL + pathname);
    }
}
