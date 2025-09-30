package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.request.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserStatusUpdateRequest;
import com.sprint.mission.discodeit.dto.request.UserUpdateRequest;
import com.sprint.mission.discodeit.dto.request.UserUpdateRequestDto;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.UserStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserStatusService userStatusService;

    @PostMapping()
    public ResponseEntity<Object> userRegister(@RequestBody UserCreateRequest userCreateRequest,
        @RequestBody Optional<BinaryContentCreateRequest> profileCreateRequest) {
        try {
            return ResponseEntity.ok(userService.create(userCreateRequest, profileCreateRequest));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Object> userUpdate(
        @RequestBody UserUpdateRequestDto userUpdateRequestDto, @PathVariable UUID userId) {
        try {
            return ResponseEntity.ok(
                userService.update(userId, userUpdateRequestDto.getUserUpdateRequest(),
                    userUpdateRequestDto.getProfileCreateRequest()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> userDelete(@PathVariable UUID userId) {
        try {
            userService.delete(userId);
            return ResponseEntity.ok("사용자가 성공적으로 삭제되었습니다.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("사용자가 존재하지 않습니다" + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<Object> userList() {
        try {
            return ResponseEntity.ok().body(userService.findAll());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PatchMapping("/{userId}/userStatus")
    public ResponseEntity<Object> updateUserStatus(@PathVariable UUID userId,
        @RequestBody UserStatusUpdateRequest userStatusUpdateRequest) {
        try {
            return ResponseEntity.ok(
                userStatusService.updateByUserId(userId, userStatusUpdateRequest));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("사용자가 존재하지 않습니다" + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
