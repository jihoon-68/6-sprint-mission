package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.User.*;
import com.sprint.mission.discodeit.dto.UserStatus.UserStatusUpdateRequest;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.UserStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Tag(name = "User", description = "User API")
@RequestMapping("/api/users")
@ResponseBody
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserStatusService userStatusService;

    @Operation(summary = "User 등록")
    @ApiResponse(responseCode = "201", description = "User가 성공적으로 생성됨")
    @ApiResponse(responseCode = "400", description = "같은 email 또는 username를 사용하는 User가 이미 존재함")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<User> userCreate(
            @Schema(description = "User 생성 정보")
            @RequestPart("userCreateRequest") UserCreateRequest userCreateRequest,
            @Schema(description = "User 프로필 이미지")
            @RequestPart(value = "profile", required = false) MultipartFile profile) {


        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(profile, userCreateRequest));
    }

    @Operation(summary = "User 수정")
    @ApiResponse(responseCode = "200", description = "User 정보가 성공적으로 수정됨")
    @ApiResponse(responseCode = "400", description = "같은 email 또는 username를 사용하는 User가 이미 존재함")
    @PatchMapping(value = "/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UpdateUserResponse> userUpdate(
            @Schema(description = "수정할 User ID")
            @PathVariable UUID userId,
            @Schema(description = "수정할 User 정보")
            @RequestPart("userUpdateRequest") UserUpdateRequest userUpdateRequest,
            @Parameter(description = "수정할 User 프로필 이미지",required = true ,allowEmptyValue = true) MultipartFile profile) {

        UpdateUserResponse update = userService.update(profile, userId, userUpdateRequest);
        return ResponseEntity.status(HttpStatus.OK).body(update);
    }

    @Operation(summary = "User 삭제")
    @ApiResponse(responseCode = "204", description = "User가 성공적으로 삭제됨")
    @ApiResponse(responseCode = "400", description = "User를 찾을 수 없음")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> userDelete(@Schema(description = "삭제할 User ID") @PathVariable UUID userId) {

        userService.delete(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "전체 User 목록 조회")
    @ApiResponse(responseCode = "200", description = "사용자 정보 조회 성공")
    @GetMapping
    public ResponseEntity<List<FindUserDTO>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @Operation(summary = "User 온라인 상태 업데이트")
    @ApiResponse(responseCode = "200", description = "User 온라인 상태가 성공적으로 업데이트됨")
    @ApiResponse(responseCode = "404", description = "해당 User의 UserStatus를 찾을 수 없음")
    @PatchMapping("/{userId}/userStatus")
    public ResponseEntity<UserStatus> userStatusUpdate(@Schema(description = "상태를 변경할 User ID") @PathVariable UUID userId, @Schema(description = "변경할 User 온라인 상태 정보") @RequestBody UserStatusUpdateRequest userStatusUpdateRequest) {
        UserStatus userStatus = userStatusService.updateByUserId(userId,userStatusUpdateRequest);
        return ResponseEntity.status(HttpStatus.OK).body(userStatus);
    }

}
