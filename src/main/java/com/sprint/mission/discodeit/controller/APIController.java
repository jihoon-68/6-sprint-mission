package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.BinaryContentService;
import com.sprint.mission.discodeit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class APIController {

    private final UserService userService;
    private final BinaryContentService binaryContentService;

    @GetMapping("/user/findAll")
    public List<User> getUsers() {
        return userService.findAll();
    }

    @GetMapping("/binaryContent/find")
    public BinaryContent getBinary(
            @RequestParam UUID binaryContentId
    ){
        return binaryContentService.find(binaryContentId);
    }

}
