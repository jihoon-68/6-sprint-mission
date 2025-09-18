package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.DTO.ReadStatus.CreateReadStatusDTO;
import com.sprint.mission.discodeit.DTO.ReadStatus.UpdateReadStatusDTO;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.service.ReadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/readStatus")
@RestController
@RequiredArgsConstructor
public class ReadStatusController {
    private final ReadStatusService readStatusService;

    @RequestMapping(value = "/", method = RequestMethod.POST ,consumes = "application/json")
    public ReadStatus readStatusCreate(@RequestBody CreateReadStatusDTO createReadStatusDTO) {
        return readStatusService.create(createReadStatusDTO);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT ,consumes = "application/json")
    public void readStatusUpdate(@RequestBody UpdateReadStatusDTO updateReadStatusDTO) {
        readStatusService.update(updateReadStatusDTO);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET ,consumes = "application/json")
    public List<ReadStatus> readStatusGet(@PathVariable UUID id) {
        return readStatusService.findAllByUserId(id);
    }


}
