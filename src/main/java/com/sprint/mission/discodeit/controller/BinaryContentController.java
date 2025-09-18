package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.service.BinaryContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RequestMapping("/binaryContent")
@RestController
@RequiredArgsConstructor
public class BinaryContentController {
    private final BinaryContentService binaryContentService;

    @RequestMapping(value = "/single:{id}", method = RequestMethod.GET, consumes = "application/json")
    public BinaryContent getBinaryContent(@PathVariable UUID id) {
        return binaryContentService.findById(id);
    }

    @RequestMapping(value = "/multi", method = RequestMethod.GET, consumes = "application/json")
    public List<BinaryContent> getAllBinaryContent(){
        return List.copyOf(binaryContentService.findAll());
    }

}
