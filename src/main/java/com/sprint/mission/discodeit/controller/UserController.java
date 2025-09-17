package com.sprint.mission.discodeit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.mission.discodeit.dto.data.UserDto;
import com.sprint.mission.discodeit.dto.request.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserStatusUpdateRequest;
import com.sprint.mission.discodeit.dto.request.UserUpdateRequest;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.UserStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/user")
public class UserController {
    private final UserService userService;
    private final UserStatusService userStatusService;
    private final ObjectMapper objectMapper;

    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable UUID userId, @RequestBody Map<String, Object> request) {
        UserUpdateRequest userUpdateRequest = objectMapper.convertValue(request.get("userUpdateRequest"), UserUpdateRequest.class);

        //Optional에 대한 map은 nullable일 경우 값이 있을 경우에만 실행한다.
        Optional<BinaryContentCreateRequest> binaryContentCreateRequest = Optional.ofNullable(request.get("binaryContentCreateRequest"))
                .map(object -> objectMapper.convertValue(object, BinaryContentCreateRequest.class));

        User user = userService.update(userId, userUpdateRequest,  binaryContentCreateRequest);

        return ResponseEntity.ok(user);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUser(@PathVariable UUID userId) {
        userService.delete(userId);

        return ResponseEntity.ok("{ message: User has been deleted }");
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.findAll();

        return ResponseEntity.ok(users);
    }

    @RequestMapping(value = "/status/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<UserStatus> updateStatus(@PathVariable UUID userId, @RequestBody UserStatusUpdateRequest request) {
        UserStatus userStatus = userStatusService.updateByUserId(userId, request);

        return ResponseEntity.ok(userStatus);
    }
}
