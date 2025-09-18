package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.service.BinaryContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class BinaryContentController {
    private final BinaryContentService binaryContentService;

    @RequestMapping("/binary/find")
    public ResponseEntity<Object> findAll(@RequestBody List<UUID> ids) {
        try {
            return ResponseEntity.ok().body(binaryContentService.findAllByIdIn(ids));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @RequestMapping("/api/binaryContent/find")
    public ResponseEntity<Object> findById(@RequestParam UUID id) {
        try {
            return ResponseEntity.ok().body(binaryContentService.find(id));
        } catch (NoSuchElementException e){
            return ResponseEntity.badRequest().body("Not found: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
