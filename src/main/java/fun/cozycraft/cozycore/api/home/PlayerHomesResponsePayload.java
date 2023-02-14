package fun.cozycraft.cozycore.api.home;

import fun.cozycraft.cozycore.models.PlayerHome;
import lombok.Data;

import java.util.Map;

@Data
public class PlayerHomesResponsePayload {
    final Map<String, PlayerHome> homes;
}
