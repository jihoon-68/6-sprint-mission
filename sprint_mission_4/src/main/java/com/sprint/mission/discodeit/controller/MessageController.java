package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.request.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageUpdateRequest;
import com.sprint.mission.discodeit.dto.request.UserUpdateRequest;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.exception.domain.user.FileProcessingException;
import com.sprint.mission.discodeit.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/message")
public class MessageController {
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @RequestMapping(path = "create", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Message> createMessage(@ModelAttribute MessageCreateRequest message,
                                                 @RequestParam(name = "attachedImages", required = false) List<MultipartFile> files) {
        var dtos = files.stream()
                .map(this::toBinaryContentIfPresent)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());

        var m = messageService.create(message, dtos);
        return ResponseEntity.status(HttpStatus.CREATED).body(m);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Message> updateMessage(@PathVariable UUID id,
                                                 @ModelAttribute MessageUpdateRequest message) {
        var m = messageService.update(id, message);

        return ResponseEntity.ok(m);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Message> deleteMessage(@PathVariable UUID id) {
        messageService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(path = "/get/{channelID}", method = RequestMethod.GET)
    public ResponseEntity<List<Message>> getMessage(@PathVariable UUID channelID) {
        var messages = messageService.findAllByChannelId(channelID);

        return ResponseEntity.ok(messages);
    }

    private Optional<BinaryContentCreateRequest> toBinaryContentIfPresent(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(new BinaryContentCreateRequest(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes()
            ));
        } catch (IOException e) {
            throw new FileProcessingException("업로드 파일을 읽는 중 오류가 발생했습니다.", e);
        }
    }
}
