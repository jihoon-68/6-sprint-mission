package com.sprint.mission.discodeit.user.controller;

import com.sprint.mission.discodeit.user.UserDto.*;
import com.sprint.mission.discodeit.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Response> createUser(@RequestBody @Valid Request request) {
        Response body = userService.createUser(request);
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Set<ResponseWithOnline>> getUsers() {
        Set<ResponseWithOnline> body = userService.getUsers();
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWithOnline> getUserById(@PathVariable UUID id) {
        ResponseWithOnline body = userService.getUserById(id);
        return ResponseEntity.ok(body);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateUserById(
            @PathVariable UUID id,
            @RequestBody @Valid Request request
    ) {
        Response body = userService.updateUserById(id, request);
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable UUID id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ResponseWithLastActivatedAt> updateUserById(
            @PathVariable UUID id,
            @RequestBody @Valid RequestWithLastActivateAt request
    ) {
        ResponseWithLastActivatedAt body = userService.updateUserById(id, request.lastActivatedAt());
        return ResponseEntity.ok(body);
    }
}
