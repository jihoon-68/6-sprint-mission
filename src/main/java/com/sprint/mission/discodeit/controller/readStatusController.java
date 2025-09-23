package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.readstatusdto.CreateReadStatusRequest;
import com.sprint.mission.discodeit.dto.readstatusdto.UpdateReadStatusRequest;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.service.ReadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/readStatuses")
@RequiredArgsConstructor
public class readStatusController {

  public final ReadStatusService readStatusService;

  @GetMapping
  public ResponseEntity<List<ReadStatus>> readStatusByUserId(
      @RequestParam UUID userId
  ) {
    List<ReadStatus> readStatusList = readStatusService.findAllByUserId(userId);
    return ResponseEntity.ok(readStatusList);
  }

  @PostMapping
  public ResponseEntity<ReadStatus> createReadStatus(
      @RequestBody CreateReadStatusRequest request
  ) {
    ReadStatus readStatus = readStatusService.create(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(readStatus);
  }

  @PatchMapping("/{readStatusId}")
  public ResponseEntity<ReadStatus> updateReadStatus(
      @PathVariable UUID readStatusId,
      @RequestBody UpdateReadStatusRequest request
  ) {
    ReadStatus updated = readStatusService.update(readStatusId, request);
    return ResponseEntity.ok(updated);
  }

}
