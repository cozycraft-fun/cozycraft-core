package fun.cozycraft.cozycore.api;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationJsonAdapter {
    @ToJson
    public JsonLocation toJson(Location location) {
        return new JsonLocation(location.getX(), location.getZ(), location.getY(), location.getWorld().getName());
    }

    @FromJson
    public Location fromJson(JsonLocation location) {
        return new Location(Bukkit.getWorld(location.world), location.x, location.y, location.z);
    }
}
