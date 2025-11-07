package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.readstatus.ReadStatusCreateRequestDto;
import com.sprint.mission.discodeit.dto.readstatus.ReadStatusResponseDto;
import com.sprint.mission.discodeit.dto.readstatus.ReadStatusUpdateRequestDto;
import com.sprint.mission.discodeit.service.ReadStatusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/readStatuses")
@RequiredArgsConstructor
public class ReadStatusController {

    private final ReadStatusService readStatusService;

    @PostMapping
    public ResponseEntity<ReadStatusResponseDto> create(@Valid @RequestBody ReadStatusCreateRequestDto dto) {
        ReadStatusResponseDto readStatus = readStatusService.create(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(readStatus);
    }

    @GetMapping("/{readStatusId}")
    public ResponseEntity<List<ReadStatusResponseDto>> findAllByUserId(@PathVariable UUID readStatusId) {
        return ResponseEntity.ok(readStatusService.findAllByUserId(readStatusId));
    }

    @PatchMapping("/users/{userId}")
    public ResponseEntity<ReadStatusResponseDto> update(@PathVariable UUID userId,
                                                        @Valid @RequestBody ReadStatusUpdateRequestDto request) {
        ReadStatusResponseDto readStatus = readStatusService.update(userId, request);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(readStatus);
    }

}
