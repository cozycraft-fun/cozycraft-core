package fun.cozycraft.cozycore.home;

import lombok.Data;

import java.util.Map;

@Data
public class PlayerHomesResponsePayload {
    final Map<String, PlayerHome> homes;
}
