package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.userdto.CreateUserRequest;
import com.sprint.mission.discodeit.dto.userdto.UpdateUserRequest;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;
import jakarta.validation.Valid;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
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
      @RequestPart("userCreateRequest") @Valid CreateUserRequest request,
      // js 파일에서 맞는 변수명을 찾아 넣어야함
      @RequestPart(value = "profile", required = false) MultipartFile profile
      // 멀티파일과 dto를 함께 쓸 경우 @RequestPart 사용, postman 테스트할때는 form-data로  { "request" : { "username" : "test" , ... } }
  ) {
    User user = userService.create(request, Optional.ofNullable(profile));
    return ResponseEntity.status(HttpStatus.CREATED).body(user);
  }

  @PatchMapping("/{userId}")
  public ResponseEntity<User> updateUser(
      @PathVariable UUID userId,
      @RequestBody @Valid UpdateUserRequest request
  ) {
    User user = userService.update(userId, request);
    return ResponseEntity.ok(user);
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<String> deleteUser(
      @PathVariable UUID userId
  ) {
    userService.delete(userId);
    return ResponseEntity.ok(userId + " 삭제되었습니다");
  }

  // todo userStatusService에서 변경?
  @PatchMapping("/{userId}/userStatus")
  public ResponseEntity<User> stateUser(
      @PathVariable UUID userId,
      @RequestParam boolean online
  ) {
    User updated = userService.updateState(userId, online);
    return ResponseEntity.ok(updated);
  }
}
