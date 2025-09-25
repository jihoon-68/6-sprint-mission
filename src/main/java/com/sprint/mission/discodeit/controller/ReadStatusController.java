package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.DTO.ReadStatus.CreateReadStatusDTO;
import com.sprint.mission.discodeit.DTO.ReadStatus.ReadStatusUpdateRequest;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.service.ReadStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "ReadStatus", description = "Message 읽음 상태 API")
@RequestMapping("/api/readStatuses")
@RestController
@RequiredArgsConstructor
public class ReadStatusController {
    private final ReadStatusService readStatusService;


    @Operation(summary = "Message 읽음 상태 생성")
    @ApiResponse(responseCode = "201", description = "Public Channel이 성공적으로 생성됨")
    @ApiResponse(responseCode = "400", description = "이미 읽음 상태가 존재함")
    @ApiResponse(responseCode = "404", description = "Channel 또는 User를 찾을 수 없음")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReadStatus> readStatusCreate(
            @Schema(description = "Message 읽음 상태 생성 정보")
            @RequestBody CreateReadStatusDTO createReadStatusDTO) {
        return ResponseEntity.ok().body(readStatusService.create(createReadStatusDTO));
    }

    @Operation(summary = "Message 읽음 상태 수정")
    @ApiResponse(responseCode = "200", description = "Message 읽음 상태가 성공적으로 수정됨")
    @ApiResponse(responseCode = "404", description = "Message 읽음 상태를 찾을 수 없음")
    @PatchMapping(value = "/{readStatusId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReadStatus> readStatusUpdate(
            @Schema(description = "수정할 읽음 상태 ID")
            @PathVariable UUID readStatusId,
            @Schema(description = "수정할 읽음 상태 정보")
            @RequestBody ReadStatusUpdateRequest  readStatusUpdateRequest) {
        return ResponseEntity.ok().body(readStatusService.update(readStatusId, readStatusUpdateRequest.newLastReadAt()));
    }

    @Operation(summary = "User의 Message 읽음 상태 목록 조회")
    @ApiResponse(responseCode = "200", description = "Message 읽음 상태 목록 조회 성공")
    @GetMapping
    public ResponseEntity<List<ReadStatus>> readStatusGet(
            @Schema(description = "조회할 User ID")
            @RequestParam("userId") UUID userId) {
        System.out.println(userId);
        return ResponseEntity.ok().body(readStatusService.findAllByUserId(userId));
    }


}
