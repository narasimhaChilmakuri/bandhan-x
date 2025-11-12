package com.bandhan.connectionsService.events;


import lombok.Data;

@Data
public class ConnectionAccepted {

    private Long fromUserId;
    private Long toUserId;

}
