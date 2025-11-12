package com.bandhan.connectionsService.events;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConnectionAccepted {

    private Long fromUserId;
    private Long toUserId;

}
