package com.bandhan.usersService.events;

import lombok.Data;

@Data
public class UserCreatedEvent {

    private Long userId;
    private String name;

}
