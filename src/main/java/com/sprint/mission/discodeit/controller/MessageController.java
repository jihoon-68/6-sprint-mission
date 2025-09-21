package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.DTO.Message.CreateMessageDTO;
import com.sprint.mission.discodeit.DTO.Message.FindMessageDTO;
import com.sprint.mission.discodeit.DTO.Message.UpdateMessageDTO;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RequestMapping("/message")
@Controller
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = "multipart/form-data")
    public String createMessage(@RequestParam List<MultipartFile> multipartFiles,
                                 CreateMessageDTO createMessageDTO) {
        messageService.create(multipartFiles,createMessageDTO);
        return"redirect:/messageP";
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = "multipart/form-data")
    public String updateMessage(@RequestParam List<MultipartFile> multipartFiles,
                              UpdateMessageDTO updateMessageDTO) {
        messageService.update(multipartFiles,updateMessageDTO);
        return "redirect:/messageP";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteMessage(@PathVariable UUID id) {

        messageService.delete(id);
        return "redirect:/messageP";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String findMessage(@PathVariable UUID id, Model model) {
        FindMessageDTO findMessageDTO =  messageService.find(id);
        model.addAttribute("message", findMessageDTO.message());
        model.addAttribute("files", findMessageDTO.files());
        return "Message/updateMessage";
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getMessage(@RequestParam("channelId") UUID id, Model model) {

        List<FindMessageDTO> messageList =messageService.findAllByChannelId(id);

        model.addAttribute("messages", messageList);
        return"Message/messageList";
    }
}
