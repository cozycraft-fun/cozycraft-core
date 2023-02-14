package fun.cozycraft.cozycore.home;

import lombok.Data;
import org.bukkit.Location;

import java.util.UUID;

@Data
public class PlayerHome {

    final UUID id;
    final String name;
    final Location location;

}
