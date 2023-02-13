package fun.cozycraft.cozycore;

import fun.cozycraft.cozycore.home.PlayerHome;
import org.bukkit.Location;

import java.util.*;

public class CozycoreState {
        public static final Map<UUID, PlayerHome[]> homes = new HashMap<>();
        public static final Map<UUID, Location> lastPositions = new HashMap<>();
        public static final List<Location> creativeBlocks = new ArrayList<>();
}
