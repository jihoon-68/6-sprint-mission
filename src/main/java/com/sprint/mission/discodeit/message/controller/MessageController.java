package com.sprint.mission.discodeit.message.controller;

import com.sprint.mission.discodeit.message.MessageDto.Request;
import com.sprint.mission.discodeit.message.MessageDto.Response;
import com.sprint.mission.discodeit.message.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<Response> createMessage(@RequestBody @Valid Request request) {
        Response body = messageService.createMessage(request);
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Set<Response>> getMessagesByChannelId(@RequestParam UUID channelId) {
        Set<Response> body = messageService.getMessagesByChannelId(channelId);
        return ResponseEntity.ok(body);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateMessageById(
            @PathVariable UUID id,
            @RequestBody @Valid Request request
    ) {
        Response body = messageService.updateMessageById(id, request);
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteMessageById(@PathVariable UUID id) {
        messageService.deleteMessageById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
