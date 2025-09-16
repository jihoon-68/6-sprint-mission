package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.service.BinaryContentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/binary-content")
public class BinaryContentController {
    private final BinaryContentService binaryContentService;

    public BinaryContentController(BinaryContentService binaryContentService) {
        this.binaryContentService = binaryContentService;
    }

    @GetMapping("/find")
    public ResponseEntity<BinaryContent> find(@RequestParam UUID binaryContentId) {
        BinaryContent content = binaryContentService.find(binaryContentId);

        if(content != null) {
            return ResponseEntity.ok(content);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/findAllByIdIn")
    public ResponseEntity<List<BinaryContent>> findAllByIdIn(@RequestParam List<UUID> binaryContentIds) {
        List<BinaryContent> contents = binaryContentService.findAllByIdIn(binaryContentIds);

        if(contents != null) {
            return ResponseEntity.ok(contents);
        }

        return ResponseEntity.notFound().build();
    }
}
