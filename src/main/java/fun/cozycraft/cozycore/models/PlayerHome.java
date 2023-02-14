package fun.cozycraft.cozycore.models;

import lombok.Data;
import org.bukkit.Location;

@Data
public class PlayerHome {

    final String id;
    final String name;
    final Location location;

}
