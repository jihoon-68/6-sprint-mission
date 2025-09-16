package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.readstatus.ReadStatusCreateRequestDto;
import com.sprint.mission.discodeit.dto.readstatus.ReadStatusResponseDto;
import com.sprint.mission.discodeit.dto.readstatus.ReadStatusUpdateRequestDto;
import com.sprint.mission.discodeit.service.ReadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/read-status")
@RequiredArgsConstructor
public class ReadStatusController {

    private final ReadStatusService readStatusService;

    @PostMapping("/{id}/")
    public ResponseEntity<ReadStatusResponseDto> create(@PathVariable UUID channelId,
                                                        @RequestBody ReadStatusCreateRequestDto dto) {
        return ResponseEntity.ok(readStatusService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ReadStatusResponseDto>> findByUserId(@PathVariable UUID id) {
        return ResponseEntity.ok(readStatusService.findAllByUserId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReadStatusResponseDto> update(@PathVariable UUID channelId,
                                                     @RequestBody ReadStatusUpdateRequestDto dto) {
        return ResponseEntity.ok(readStatusService.update(dto));
    }

}
