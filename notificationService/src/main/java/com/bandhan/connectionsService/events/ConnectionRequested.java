package com.bandhan.connectionsService.events;

import lombok.Data;

@Data
public class ConnectionRequested {

    private Long fromUserId;
    private Long toUserId;
}
