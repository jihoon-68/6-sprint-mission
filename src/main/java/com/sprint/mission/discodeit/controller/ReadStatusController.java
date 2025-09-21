package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.DTO.ReadStatus.CreateReadStatusDTO;
import com.sprint.mission.discodeit.DTO.ReadStatus.UpdateReadStatusDTO;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.service.ReadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/readStatus")
@Controller
@RequiredArgsConstructor
public class ReadStatusController {
    private final ReadStatusService readStatusService;

    @RequestMapping(value = "", method = RequestMethod.POST )
    public String readStatusCreate(@ModelAttribute CreateReadStatusDTO createReadStatusDTO) {
        System.out.println(createReadStatusDTO);
        readStatusService.create(createReadStatusDTO);
        return "redirect:/readStatusP/getStatus";
    }

    @RequestMapping(value = "", method = RequestMethod.PUT )
    public String readStatusUpdate(@ModelAttribute UpdateReadStatusDTO updateReadStatusDTO) {
        System.out.println(updateReadStatusDTO);
        readStatusService.update(updateReadStatusDTO);
        return "redirect:/readStatusP/getStatus";
    }

    @RequestMapping(value="/find:{id}")
    public String readStatusFind(@PathVariable UUID id, Model model) {
        ReadStatus readStatus = readStatusService.findById(id);
        model.addAttribute("findReadStatus",readStatus);
        return "ReadStatus/updateStatus";
    }

    @RequestMapping(value = "", method = RequestMethod.GET )
    public String readStatusGet(@RequestParam("userId") UUID id , Model model) {

        List<ReadStatus> readStatusList = readStatusService.findAllByUserId(id);
        model.addAttribute("readStatus", readStatusList);
        return "ReadStatus/StatusList";
    }


}
