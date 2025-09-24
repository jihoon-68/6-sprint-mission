package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.request.ReadStatusCreateRequest;
import com.sprint.mission.discodeit.dto.request.ReadStatusUpdateRequest;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.service.ReadStatusService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/readStatus")
public class ReadStatusController {

    private final ReadStatusService readStatusService;
    public ReadStatusController(ReadStatusService readStatusService) {
        this.readStatusService = readStatusService;
    }

    @PostMapping("/create")
    public ReadStatus  create(@RequestBody ReadStatusCreateRequest request){
        return readStatusService.create(request);
    }

    @PutMapping("/update")
    public ReadStatus update(@RequestBody ReadStatusUpdateRequest request, @RequestParam UUID readStatusId){
        return readStatusService.update(readStatusId,  request);
    }

    @GetMapping("/findAllByUserId")
    public List<ReadStatus> findAllByUserId(@RequestParam UUID userId){
        return readStatusService.findAllByUserId(userId);
    }
}
