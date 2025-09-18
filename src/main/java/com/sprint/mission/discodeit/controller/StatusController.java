package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.request.ReadStatusCreateRequest;
import com.sprint.mission.discodeit.dto.request.ReadStatusUpdateRequest;
import com.sprint.mission.discodeit.service.ReadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.NoSuchElementException;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class StatusController {
    public final ReadStatusService readStatusService;

    @RequestMapping("/status/create")
    public ResponseEntity<Object> createStatus(@RequestBody ReadStatusCreateRequest readStatusCreateRequest) {
        try {
            readStatusService.create(readStatusCreateRequest);
            return ResponseEntity.ok().body("Status created successfully");
        } catch (NoSuchElementException e){
            return ResponseEntity.badRequest().body("Not found: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @RequestMapping("/status/update/{statusId}")
    public ResponseEntity<Object> updateStatus(@PathVariable String statusId, @RequestBody ReadStatusUpdateRequest readStatusUpdateRequest) {
        try {
            readStatusService.update(UUID.fromString(statusId), readStatusUpdateRequest);
            return ResponseEntity.ok().body("Status updated successfully");
        } catch (NoSuchElementException e){
            return ResponseEntity.badRequest().body("Not found: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @RequestMapping("/status/{statusId}")
    public ResponseEntity<Object> deleteStatus(@PathVariable String statusId) {
        try {
            readStatusService.delete(UUID.fromString(statusId));
            return ResponseEntity.ok().body("Status deleted successfully");
        } catch (NoSuchElementException e){
            return ResponseEntity.badRequest().body("Not found: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @RequestMapping("/status/list/{userId}")
    public ResponseEntity<Object> listStatusesByUser(@PathVariable String userId) {
        try {
            return ResponseEntity.ok().body(readStatusService.findAllByUserId(UUID.fromString(userId)));
        } catch (NoSuchElementException e){
            return ResponseEntity.badRequest().body("Not found: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


}
