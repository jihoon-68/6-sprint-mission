package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.request.ReadStatusCreateRequest;
import com.sprint.mission.discodeit.dto.request.ReadStatusUpdateRequest;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.service.ReadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/readStatuses")
public class StatusController {

    public final ReadStatusService readStatusService;

    @PostMapping("/status/create")
    public ResponseEntity<Object> createStatus(
        @RequestBody ReadStatusCreateRequest readStatusCreateRequest) {
        try {
            ReadStatus readStatus = readStatusService.create(readStatusCreateRequest);
            return ResponseEntity.ok().body(readStatus);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{readStatusId}")
    public ResponseEntity<Object> updateStatus(@PathVariable UUID readStatusId,
        @RequestBody ReadStatusUpdateRequest readStatusUpdateRequest) {
        try {
            ReadStatus readStatus = readStatusService.update(readStatusId, readStatusUpdateRequest);
            return ResponseEntity.ok().body(readStatus);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<Object> listStatusesByUser(@RequestParam String userId) {
        try {
            return ResponseEntity.ok()
                .body(readStatusService.findAllByUserId(UUID.fromString(userId)));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("Not found: " + e.getMessage());
        }
    }


}
