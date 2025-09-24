package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.request.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageUpdateRequest;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.MessageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Message> getMessages(@RequestParam UUID channelId) {
        return messageService.findAllByChannelId(channelId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Message createMessage(@RequestParam String content,
                                 @RequestParam UUID channelId,
                                 @RequestParam UUID authorId,
                                 @RequestParam(required = false) List<MultipartFile> files) throws IOException {
        MessageCreateRequest messageCreateRequest = new MessageCreateRequest(content, channelId, authorId);

        List<BinaryContentCreateRequest> binaryContentCreateRequests;
        if (files != null && !files.isEmpty()) {
            binaryContentCreateRequests = files.stream().map(file -> {
                try {
                    return new BinaryContentCreateRequest(file.getOriginalFilename(), file.getContentType(), file.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException("Failed to read file", e);
                }
            }).collect(Collectors.toList());
        } else {
            binaryContentCreateRequests = Collections.emptyList();
        }

        return messageService.create(messageCreateRequest, binaryContentCreateRequests);
    }

    @RequestMapping(value = "/{messageId}", method = RequestMethod.PATCH)
    public Message updateMessage(@PathVariable UUID messageId, @RequestBody MessageUpdateRequest request) {
        return messageService.update(messageId, request);
    }

    @RequestMapping(value = "/{messageId}", method = RequestMethod.DELETE)
    public void deleteMessage(@PathVariable UUID messageId) {
        messageService.delete(messageId);
    }
}
