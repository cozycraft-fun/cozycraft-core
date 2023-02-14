package fun.cozycraft.cozycore.home;

import lombok.Data;

import java.util.HashMap;

@Data
public class PlayerHomesResponsePayload {
    final HashMap<String, PlayerHome> homes;
}
