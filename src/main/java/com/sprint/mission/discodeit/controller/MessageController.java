package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.request.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageUpdateRequest;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.BinaryContentService;
import com.sprint.mission.discodeit.service.MessageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/message")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/create")
    public Message create(@RequestPart MessageCreateRequest request
                        , @RequestParam(required = false) List<MultipartFile> files) {

        return messageService.create(request,files);
    }

    @PutMapping("/update")
    public Message update(@RequestParam UUID messageId, @RequestBody MessageUpdateRequest request)
    {
        return messageService.update(messageId,request);
    }

    @DeleteMapping("delete")
    public void delete(@RequestParam UUID messageId) {
        messageService.delete(messageId);
    }

    @GetMapping("/findAllByChannelId")
    public List<Message> findAllByChannelId(@RequestParam UUID channelId) {
        return messageService.findAllByChannelId(channelId);
    }
}
