package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.service.BinaryContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/binaryContent")
@Controller
@RequiredArgsConstructor
public class BinaryContentController {
    private final BinaryContentService binaryContentService;

    @RequestMapping(value = "/single:{id}", method = RequestMethod.GET)
    public String getBinaryContent(@PathVariable UUID id, Model model) {
        model.addAttribute("imageId", binaryContentService.findById(id).getFilePath());

        return "imageDetail";
    }

    @RequestMapping(value = "/multi", method = RequestMethod.GET)
    public String getAllBinaryContent(Model model){
        List<String> imageList = binaryContentService.findAll().stream()
                        .map(BinaryContent::getFilePath)
                                .toList();
        model.addAttribute("imageIds", imageList);

        return "imageList";
    }

}
