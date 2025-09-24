package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.data.UserDto;
import com.sprint.mission.discodeit.dto.request.UserCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserStatusUpdateRequest;
import com.sprint.mission.discodeit.dto.request.UserUpdateRequest;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.UserStatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final UserStatusService userStatusService;

    public UserController(UserService userService, UserStatusService userStatusService) {
        this.userService = userService;
        this.userStatusService = userStatusService;
    }

    @PostMapping("/create")
    public User createUser(String username, String email, String password, @RequestPart(required = false) MultipartFile profile) {
        UserCreateRequest request = new UserCreateRequest(username, email, password);
        return userService.create(request, Optional.ofNullable(profile));
    }

    @PostMapping("/update")
    public User updateUser(@RequestParam String newUserName,
                           @RequestParam String newEmail,
                           @RequestParam String newPassword,
                           @RequestParam UUID userId,
                           @RequestPart(required = false) MultipartFile profile) {

        UserUpdateRequest request = new UserUpdateRequest(newUserName, newEmail, newPassword);
        return userService.update(userId, request, Optional.ofNullable(profile));

    }

    @DeleteMapping("/delete")
    public void deleteUser(@RequestParam UUID userId) {
        userService.delete(userId);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping("/updateUserStatus")
    public UserStatus updateUserStatus(@RequestParam UUID userId, @RequestBody UserStatusUpdateRequest request) {
        return userStatusService.updateByUserId(userId, request);
    }

}
