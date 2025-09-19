package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.messagedto.CreateMessageDto;
import com.sprint.mission.discodeit.dto.messagedto.UpdateMessageDto;
import com.sprint.mission.discodeit.dto.readstatusdto.UpdateReadStatusDto;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.service.ReadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/readStatuses")
@RequiredArgsConstructor
public class readStatusController {

    public final ReadStatusService readStatusService;

    @GetMapping("{userId}")
    public ResponseEntity<List<ReadStatus>> readStatusByUserId(
            @PathVariable UUID userId
    ){
        List<ReadStatus> readStatusList = readStatusService.findAllByUserId(userId);
        return ResponseEntity.ok(readStatusList);
    }

}
