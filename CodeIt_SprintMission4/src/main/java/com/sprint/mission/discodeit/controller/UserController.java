package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.data.UserDto;
import com.sprint.mission.discodeit.dto.request.UserCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserUpdateRequest;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<UserDto> getUsers() {
        return userService.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public User createUser(@RequestBody UserCreateRequest request) {
        return userService.create(request, Optional.empty());
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.PATCH)
    public User updateUser(@PathVariable UUID userId, @RequestBody UserUpdateRequest request) {
        return userService.update(userId, request, Optional.empty());
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable UUID userId) {
        userService.delete(userId);
    }
}
