package fun.cozycraft.cozycore.refer;

import lombok.Data;

@Data
public class ReferFriendRequestPayload {
    final String username;
    final String type;
    final String referredBy;
}
