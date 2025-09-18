package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.request.LoginRequest;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @RequestMapping("/auth/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) {
        try {
            User authResponse = authService.login(loginRequest);
            return ResponseEntity.ok().body("Login successful: " + authResponse.getUsername());
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("Not found: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Wrong password: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
