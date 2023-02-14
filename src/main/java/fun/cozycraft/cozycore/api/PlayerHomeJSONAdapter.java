package fun.cozycraft.cozycore.api;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;
import fun.cozycraft.cozycore.models.PlayerHome;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class PlayerHomeJSONAdapter {
    @ToJson
    public PlayerHomeJSON toJson(PlayerHome home) {
        Location location = home.getLocation();
        return new PlayerHomeJSON(home.getId(), home.getName(), location.getX(), location.getZ(), location.getY(), location.getWorld().getName());
    }

    @FromJson
    public PlayerHome fromJson(PlayerHomeJSON json) {
        Location location = new Location(Bukkit.getWorld(json.worldName), json.x, json.y, json.z);
        return new PlayerHome(json.id, json.name, location);
    }
}
