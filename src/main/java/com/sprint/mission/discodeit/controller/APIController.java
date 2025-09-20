package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.userdto.UserResponse;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.BinaryContentService;
import com.sprint.mission.discodeit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<UserResponse>> getUsers() {
        List<User> userList = userService.findAll();
        List<UserResponse> response = userList.stream()
                .map(UserResponse::fromEntity)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/binaryContent/find")
    public ResponseEntity<BinaryContent> getBinary(
            @RequestParam UUID binaryContentId
    ){
        BinaryContent binaryContent = binaryContentService.find(binaryContentId);
        return ResponseEntity.ok(binaryContent);
    }

}
