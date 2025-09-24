package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.userdto.CreateUserRequest;
import com.sprint.mission.discodeit.dto.userdto.UpdateUserRequest;
import com.sprint.mission.discodeit.dto.userdto.UserResponse;
import com.sprint.mission.discodeit.dto.userstatusdto.UpdateUserStatus;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.UserStatusService;
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
  private final UserStatusService userStatusService;

  @GetMapping
  public ResponseEntity<List<UserResponse>> readUser() {
    List<User> userList = userService.findAll();
    List<UserResponse> response = userList.stream()
        .map(UserResponse::fromEntity)
        .toList();
    return ResponseEntity.ok(response);

  }

  @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<User> createUser(
      // @RequestPart의 경우 js 파일에서 맞는 변수명을 찾아 넣어야함
      // 멀티파일과 dto를 함께 쓸 경우 @RequestPart 사용, postman 테스트할때는 form-data로  { "request" : { "username" : "test" , ... } }
      @RequestPart("userCreateRequest") @Valid CreateUserRequest request,
      @RequestPart(value = "profile", required = false) MultipartFile profile
  ) {
    User user = userService.create(request, Optional.ofNullable(profile));
    return ResponseEntity.status(HttpStatus.CREATED).body(user);
  }

  @PatchMapping(value = "/{userId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,
      MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<User> updateUser(
      @PathVariable UUID userId,
      @RequestPart("userUpdateRequest") @Valid UpdateUserRequest request,
      @RequestPart(value = "profile", required = false) MultipartFile profile
  ) {
    User user = userService.update(userId, request,
        Optional.ofNullable(profile));
    return ResponseEntity.ok(user);
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<String> deleteUser(
      @PathVariable UUID userId
  ) {
    userService.delete(userId);
    return ResponseEntity.ok(userId + " 삭제되었습니다");
  }

  @PatchMapping("/{userId}/userStatus")
  public ResponseEntity<UserStatus> stateUser(
      @PathVariable UUID userId,
      @RequestBody UpdateUserStatus updateUserStatus
  ) {
    System.out.println(userId);
    UserStatus updated = userStatusService.update(userId, updateUserStatus);
    return ResponseEntity.ok(updated);
  }
}
