package com.sprint.mission.discodeit.binarycontent.controller;

import com.sprint.mission.discodeit.binarycontent.BinaryContentDto.Response;
import com.sprint.mission.discodeit.binarycontent.service.BinaryContentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/binary-contents")
public class BinaryContentController {

    private final BinaryContentService binaryContentService;

    public BinaryContentController(BinaryContentService binaryContentService) {
        this.binaryContentService = binaryContentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getBinaryContentById(@PathVariable UUID id) {
        Response body = binaryContentService.getBinaryContentById(id);
        return ResponseEntity.ok(body);
    }
}
