package com.sprint.mission.discodeit.controller;


import com.sprint.mission.discodeit.dto.authdto.AuthRequest;
import com.sprint.mission.discodeit.dto.userdto.CreateUserRequest;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.AuthService;
import com.sprint.mission.discodeit.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<User> getLogin(
      @RequestBody AuthRequest request
  ) {
    User user = authService.login(request);
    return ResponseEntity.ok(user);
  }

}
