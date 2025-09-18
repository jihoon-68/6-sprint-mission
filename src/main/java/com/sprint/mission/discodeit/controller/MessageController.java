package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.DTO.Message.CreateMessageDTO;
import com.sprint.mission.discodeit.DTO.Message.UpdateMessageDTO;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RequestMapping("/message")
@RestController
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = "multipart/form-data")
    public Message createMessage(@RequestParam List<MultipartFile> multipartFiles,
                                 CreateMessageDTO createMessageDTO) {
        return messageService.create(multipartFiles,createMessageDTO);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT, consumes = "multipart/form-data")
    public void updateMessage(@RequestParam List<MultipartFile> multipartFiles,
                              UpdateMessageDTO updateMessageDTO) {
        messageService.update(multipartFiles,updateMessageDTO);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = "application/json")
    public void deleteMessage(@PathVariable UUID id) {
        messageService.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, consumes = "application/json")
    public List<Message> getMessage(@PathVariable UUID id) {
        return messageService.findAllByChannelId(id);
    }
}
