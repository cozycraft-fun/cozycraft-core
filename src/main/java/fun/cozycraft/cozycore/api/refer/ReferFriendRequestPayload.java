package fun.cozycraft.cozycore.api.refer;

import lombok.Data;

@Data
public class ReferFriendRequestPayload {
    final String username;
    final String type;
    final String referredBy;
}
