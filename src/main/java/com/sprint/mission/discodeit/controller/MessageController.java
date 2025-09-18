package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.data.MessageSendDto;
import com.sprint.mission.discodeit.dto.request.MessageUpdateRequest;
import com.sprint.mission.discodeit.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.NoSuchElementException;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @RequestMapping("/message/send")
    public ResponseEntity<Object> sendMessage(@RequestBody MessageSendDto messageSendDto) {
        try{
            messageService.create(messageSendDto.getMessageCreateRequest(), messageSendDto.getBinaryContentCreateRequests());
            return ResponseEntity.ok().body("Message sent successfully");
        } catch (NoSuchElementException e){
            return new ResponseEntity<>("Not found" + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @RequestMapping("/message/update/{messageId}")
    public ResponseEntity<Object> updateMessage(@PathVariable String messageId, @RequestBody MessageUpdateRequest messageUpdateRequest){
        try{
            messageService.update(UUID.fromString(messageId), messageUpdateRequest);
            return ResponseEntity.ok().body("Message updated successfully");
        } catch (NoSuchElementException e){
            return new ResponseEntity<>("Not found" + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @RequestMapping("/message/delete/{messageId}")
    public ResponseEntity<Object> deleteMessage(@PathVariable String messageId){
        try{
            messageService.delete(UUID.fromString(messageId));
            return ResponseEntity.ok().body("Message deleted successfully");
        } catch (NoSuchElementException e){
            return new ResponseEntity<>("Not found" + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @RequestMapping("/message/list/{channelId}")
    public ResponseEntity<Object> listMessagesByChannel(@PathVariable String channelId){
        try{
            return ResponseEntity.ok().body(messageService.findAllByChannelId(UUID.fromString(channelId)));
        } catch (NoSuchElementException e){
            return new ResponseEntity<>("Not found" + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
