package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.service.BinaryContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class BinaryContentController {
    private final BinaryContentService binaryContentService;

    @Autowired
    public BinaryContentController(BinaryContentService binaryContentService) {
        this.binaryContentService = binaryContentService;
    }

    @RequestMapping(path = "/content", method = RequestMethod.GET)
    public ResponseEntity<List<BinaryContent>> getBinaryContent(@RequestParam("ids") List<UUID> ids) {
        var result = binaryContentService.findAllByIdIn(ids);

        return ResponseEntity.ok(result);
    }

    @RequestMapping(path = "/api/binaryContent/find", method = RequestMethod.GET)
    public ResponseEntity<BinaryContent> findBinaryContent(@RequestParam("binaryContentId") UUID id) {
        var result = binaryContentService.find(id);
        return ResponseEntity.ok(result);
    }
}
