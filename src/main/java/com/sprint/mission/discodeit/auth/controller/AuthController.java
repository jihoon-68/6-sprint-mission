package com.sprint.mission.discodeit.auth.controller;

import com.sprint.mission.discodeit.auth.AuthDto.Request;
import com.sprint.mission.discodeit.auth.AuthDto.Response;
import com.sprint.mission.discodeit.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody @Valid Request request) {
        Response body = authService.login(request);
        return ResponseEntity.ok(body);
    }
}
