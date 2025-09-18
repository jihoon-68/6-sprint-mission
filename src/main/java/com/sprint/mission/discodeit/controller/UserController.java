package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.binarycontent.CreateProfileImageDto;
import com.sprint.mission.discodeit.dto.userdto.CreateUserDto;
import com.sprint.mission.discodeit.dto.userdto.FindUserDto;
import com.sprint.mission.discodeit.dto.userdto.UpdateUserDto;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<User> readUser(
            @PathVariable UUID userId) {
        User user = userService.find(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping
    public ResponseEntity<User> createUser(
            @RequestBody CreateUserDto createUserDto,
            @RequestBody CreateProfileImageDto createProfileImageDto
    ) {
        User user = userService.create(createUserDto,createProfileImageDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(
            @PathVariable UUID userId,
            @RequestBody UpdateUserDto updateUserDto
    ) {
        User user = userService.update(userId, updateUserDto);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(
            @PathVariable UUID userId
    ){
        userService.delete(userId);
        return ResponseEntity.ok(userId +" 삭제되었습니다");
    }


}
