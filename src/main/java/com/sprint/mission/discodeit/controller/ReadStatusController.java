package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.request.ReadStatusCreateRequest;
import com.sprint.mission.discodeit.dto.request.ReadStatusUpdateRequest;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.service.ReadStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Tag(name = "ReadStatus")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/readStatuses")
public class ReadStatusController {

    private final ReadStatusService readStatusService;

    @Operation(summary = "ReadStatus 생성", operationId = "createReadStatus")
    @PostMapping
    public ResponseEntity<ReadStatus> create(@RequestBody ReadStatusCreateRequest request) {
        ReadStatus createdReadStatus = readStatusService.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdReadStatus);
    }

    @Operation(summary = "특정 ReadStatus 조회 (userId, channelId)", operationId = "findReadStatus")
    @GetMapping(path = "/{userId}/{channelId}")
    public ResponseEntity<ReadStatus> find(
            @PathVariable("userId") UUID userId,
            @PathVariable("channelId") UUID channelId) {
        ReadStatus readStatus = readStatusService.find(userId, channelId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(readStatus);
    }

    @Operation(summary = "ReadStatus 수정", operationId = "updateReadStatus")
    @PutMapping(path = "/{userId}/{channelId}")
    public ResponseEntity<ReadStatus> update(
            @PathVariable("userId") UUID userId,
            @PathVariable("channelId") UUID channelId,
            @RequestBody ReadStatusUpdateRequest request) {

        ReadStatus updatedReadStatus = readStatusService.update(userId, channelId, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedReadStatus);
    }

    @Operation(summary = "모든 ReadStatus 를 UserId로 조회", operationId = "findAllReadStatusByUserId")
    @GetMapping
    public ResponseEntity<List<ReadStatus>> findAllByUserId(@RequestParam("userId") UUID userId) {
        List<ReadStatus> readStatuses = readStatusService.findAllByUserId(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(readStatuses);
    }

    @Operation(summary = "ReadStatus 삭제 (userId, channelId)", operationId = "deleteReadStatus")
    @DeleteMapping(path = "/{userId}/{channelId}")
    public ResponseEntity<Void> delete(
            @PathVariable("userId") UUID userId,
            @PathVariable("channelId") UUID channelId) {
        readStatusService.delete(userId, channelId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}