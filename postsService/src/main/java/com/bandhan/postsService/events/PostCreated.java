package com.bandhan.postsService.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostCreated {

    private Long postId;
    private Long ownerUserId;
    private Long userId;
    private String content;

}
