package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.service.BinaryContentService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/binaryContent")
public class BinaryContentController {
    private final BinaryContentService binaryContentService;

    public BinaryContentController(BinaryContentService binaryContentService) {
        this.binaryContentService = binaryContentService;
    }

    @GetMapping("/find")
    public ResponseEntity<BinaryContent> find(@RequestParam UUID binaryContentId) {
        BinaryContent binaryContent = binaryContentService.find(binaryContentId);

        if(binaryContent != null)
            return ResponseEntity.ok(binaryContent);

        return ResponseEntity.notFound().build();
    }

    @GetMapping("findAllByIdIn")
    public ResponseEntity<List<BinaryContent>> findAll(@RequestParam List<UUID> binaryContentIds) {
        List<BinaryContent> contents = binaryContentService.findAllByIdIn(binaryContentIds);

        if(contents != null && contents.size() > 0)
            return ResponseEntity.ok(contents);

        return ResponseEntity.notFound().build();
    }
}
