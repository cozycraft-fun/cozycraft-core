package fun.cozycraft.cozycore.api;

import lombok.Data;

@Data
public class JsonLocation {
    final double x;
    final double z;
    final double y;
    final String world;
}
