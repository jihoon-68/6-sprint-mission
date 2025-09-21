package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.service.BinaryContentService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/downloads")
public class BinaryContentController {
    private final BinaryContentService binaryContentService;

    public BinaryContentController(BinaryContentService binaryContentService) {
        this.binaryContentService = binaryContentService;
    }

    @RequestMapping(value = "/{fileId}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getBinaryContent(@PathVariable UUID fileId) {
        BinaryContent binaryContent = binaryContentService.find(fileId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(binaryContent.getContentType()));
        headers.setContentDispositionFormData("attachment", binaryContent.getFileName());
        return new ResponseEntity<>(binaryContent.getBytes(), headers, HttpStatus.OK);
    }
}
