package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.service.ReadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/readStatuses")
@RequiredArgsConstructor
public class readStatusController {

    public final ReadStatusService readStatusService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<ReadStatus>> readStatusByUserId(
            @PathVariable UUID userId
    ){
        List<ReadStatus> readStatusList = readStatusService.findAllByUserId(userId);
        return ResponseEntity.ok(readStatusList);
    }

}
