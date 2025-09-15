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
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final UserStatusService userStatusService;

    public UserController(UserService userService, UserStatusService userStatusService) {
        this.userService = userService;
        this.userStatusService = userStatusService;
    }

    @PostMapping("/create")
    public User createUser(String username, String email, String password, @RequestPart(required = false) MultipartFile profileCreateRequest) {
        UserCreateRequest request = new UserCreateRequest(username, email, password);
        return userService.create(request, Optional.ofNullable(profileCreateRequest));
    }

    @PostMapping("/update")
    public User updateUser(@RequestParam String newUsername, @RequestParam String newEmail, @RequestParam String newPassword
                        , @RequestParam(value = "profileUpdateRequest", required = false) MultipartFile profileUpdateRequest
                        , @RequestParam UUID userId) {

        UserUpdateRequest  request = new UserUpdateRequest(newUsername, newEmail, newPassword);
        return userService.update(userId, request, Optional.ofNullable(profileUpdateRequest));

    }

    @DeleteMapping("/delete")
    public void deleteUser(@RequestParam UUID userId) {
        userService.delete(userId);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<UserDto>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping("/updateUserStatusByUserId")
    public UserStatus updateUserStatusByUserId(@RequestParam UUID userId, @RequestBody UserStatusUpdateRequest request){
        return userStatusService.updateByUserId(userId, request);
    }
}
