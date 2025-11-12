package com.bandhan.postsService.client;


import com.bandhan.postsService.dto.PersonDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "connections-service", path = "/connections/core")
public interface ConnectionsServiceClient {

    @GetMapping("/{userId}/first-degree")
    List<PersonDto> getFirstDegreeConnections(@PathVariable Long userId);


}
