package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.message.MessageCreateRequestDto;
import com.sprint.mission.discodeit.dto.message.MessageResponseDto;
import com.sprint.mission.discodeit.dto.message.MessageUpdateRequestDto;
import com.sprint.mission.discodeit.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/create/")
    public ResponseEntity<MessageResponseDto> create(@RequestBody MessageCreateRequestDto dto) {
        return ResponseEntity.ok(messageService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<MessageResponseDto>> findByChannelId(@PathVariable UUID id) {
        return ResponseEntity.ok(messageService.findByChannelId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponseDto> update(@PathVariable UUID id,
                                                     @RequestBody MessageUpdateRequestDto dto) {
        return ResponseEntity.ok(messageService.update(id, dto));
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        messageService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
