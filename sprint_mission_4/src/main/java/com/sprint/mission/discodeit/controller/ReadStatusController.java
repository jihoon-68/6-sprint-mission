package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.request.ReadStatusCreateRequest;
import com.sprint.mission.discodeit.dto.request.ReadStatusUpdateRequest;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.service.ReadStatusService;
import jdk.jshell.Snippet;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "readStatus")
public class ReadStatusController {
    private final ReadStatusService readStatusService;

    @Autowired
    public ReadStatusController(ReadStatusService readStatusService) {
        this.readStatusService = readStatusService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ReadStatus> readStatus(@RequestBody ReadStatusCreateRequest readStatus){
        var result = readStatusService.create(readStatus);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @RequestMapping(path = "/{uuid}", method = RequestMethod.GET)
    public ResponseEntity<List<ReadStatus>> readStatus(@PathVariable UUID uuid){
        var result = readStatusService.findAllByUserId(uuid);

        return ResponseEntity.ok(result);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ReadStatus> updateStatus(@PathVariable UUID id,
                                                   @RequestBody ReadStatusUpdateRequest readStatus){
        var result = readStatusService.update(id, readStatus);

        return ResponseEntity.ok(result);
    }
}
