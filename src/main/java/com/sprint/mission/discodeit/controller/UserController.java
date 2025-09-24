package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.userdto.CreateUser;
import com.sprint.mission.discodeit.dto.userdto.UpdateUser;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> readUser() {
        List<User> userList = userService.findAll();
        return ResponseEntity.ok(userList);
    }

    @PostMapping
    public ResponseEntity<User> createUser(
            @RequestBody @Valid CreateUser createUser
    ) {
        User user = userService.create(createUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(
            @PathVariable UUID userId,
            @RequestBody @Valid UpdateUser updateUser
    ) {
        User user = userService.update(userId, updateUser);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(
            @PathVariable UUID userId
    ){
        userService.delete(userId);
        return ResponseEntity.ok(userId +" 삭제되었습니다");
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<User> stateUser(
            @PathVariable UUID userId,
            @RequestParam boolean online
    ){
        User updated = userService.updateState(userId,online);
        return ResponseEntity.ok(updated);
    }
}
