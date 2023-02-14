package fun.cozycraft.cozycore.api;

import lombok.Data;

@Data
public class PlayerHomeJSON {
    final String id;
    final String name;
    final double x;
    final double z;
    final double y;
    final String worldName;
}
