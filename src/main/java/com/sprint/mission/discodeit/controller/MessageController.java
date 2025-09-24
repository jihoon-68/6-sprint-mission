package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.messagedto.CreateMessage;
import com.sprint.mission.discodeit.dto.messagedto.UpdateMessage;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping("{channelId}")
    public ResponseEntity<List<Message>> readMessageByChannelId(
            @PathVariable UUID channelId
    ){
        List<Message> messageList = messageService.findAllByChannelId(channelId);
        return ResponseEntity.ok(messageList);
    }

    @PostMapping
    public ResponseEntity<Message> createChannel(
            @RequestBody @Valid CreateMessage createMessage
            ) {
        Message message = messageService.create(createMessage);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @PutMapping("/{messageId}")
    public ResponseEntity<Message> updateMessage(
            @PathVariable UUID messageId,
            @RequestBody @Valid UpdateMessage updateMessage
            ) {
        Message message = messageService.update(messageId, updateMessage);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<String> deleteChannel(
            @PathVariable UUID messageId
            ){
        messageService.delete(messageId);
        return ResponseEntity.ok(messageId +" 삭제되었습니다");
    }

}
