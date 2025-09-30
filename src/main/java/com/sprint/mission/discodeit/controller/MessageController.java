package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.data.MessageSendDto;
import com.sprint.mission.discodeit.dto.request.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageUpdateRequest;
import com.sprint.mission.discodeit.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    @PostMapping()
    public ResponseEntity<Object> sendMessage(@RequestBody MessageSendDto messageSendDto) {
        try {
            return ResponseEntity.ok()
                .body(messageService.create(messageSendDto.getMessageCreateRequest(),
                    messageSendDto.getBinaryContentCreateRequests()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PatchMapping("/{messageId}")
    public ResponseEntity<Object> updateMessage(@PathVariable UUID messageId,
        @RequestBody MessageUpdateRequest messageUpdateRequest) {
        try {
            return ResponseEntity.ok().body(messageService.update(messageId, messageUpdateRequest));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Not found" + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<Object> deleteMessage(@PathVariable UUID messageId) {
        try {
            messageService.delete(messageId);
            return ResponseEntity.ok().body("Message deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<Object> listMessagesByChannel(@RequestParam UUID channelId) {
        try {
            return ResponseEntity.ok().body(messageService.findAllByChannelId(channelId));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
