package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.request.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageUpdateRequest;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.MessageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/create")
    public Message create(@RequestParam UUID channelId,
                          @RequestParam UUID authorId,
                          String content,
                          @RequestParam(required = false) List<MultipartFile> files) {

        MessageCreateRequest request = new MessageCreateRequest(content, channelId, authorId);
        return messageService.create(request, files);
    }

    @PostMapping("/update")
    public Message updateMessage(@RequestParam UUID messageId,
                                 @RequestBody MessageUpdateRequest request) {

        return messageService.update(messageId,request);
    }

    @DeleteMapping("/delete")
    public void deleteMessage(@RequestParam UUID messageId) {
        messageService.delete(messageId);
    }

    @GetMapping("/findAllByChannelId")
    public List<Message> findAllByChannelId(@RequestParam UUID channelId) {
        return messageService.findAllByChannelId(channelId);
    }

}
