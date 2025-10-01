package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentCreateRequestDto;
import com.sprint.mission.discodeit.dto.message.MessageCreateRequestDto;
import com.sprint.mission.discodeit.dto.message.MessageResponseDto;
import com.sprint.mission.discodeit.dto.message.MessageUpdateRequestDto;
import com.sprint.mission.discodeit.entity.BinaryContentType;
import com.sprint.mission.discodeit.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageResponseDto> create(@RequestPart("messageCreateRequest") MessageCreateRequestDto dto,
                                                     @RequestPart(value = "attachments", required = false)  List<MultipartFile> attachments) {

        List<BinaryContentCreateRequestDto> attachmentRequests = Optional.ofNullable(attachments)
                .map(files -> files.stream()
                        .map(file -> {
                            try {
                                return new BinaryContentCreateRequestDto(
                                        file.getOriginalFilename(),
                                        file.getContentType(),
                                        BinaryContentType.ATTACH_IMAGE,
                                        file.getBytes()
                                );
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .toList())
                .orElse(new ArrayList<>());

        MessageResponseDto message = messageService.create(dto, attachmentRequests);
        return ResponseEntity
                .status(HttpStatus.CREATED) // 201 Created
                .body(message);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<MessageResponseDto>> findAllByChannelId(@PathVariable UUID id) {
        return ResponseEntity.ok(messageService.findByChannelId(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MessageResponseDto> update(@PathVariable UUID id,
                                                     @RequestBody MessageUpdateRequestDto dto) {
        MessageResponseDto message = messageService.update(id, dto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(message);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        messageService.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
