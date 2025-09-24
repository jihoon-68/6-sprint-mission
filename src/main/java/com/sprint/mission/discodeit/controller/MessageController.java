package com.sprint.mission.discodeit.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.mission.discodeit.dto.request.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageUpdateRequest;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.swing.text.html.Option;
import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/message")
public class MessageController {
    private final MessageService messageService;
    private final ObjectMapper objectMapper;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Message> create(@RequestBody Map<String, Object> request) {
        MessageCreateRequest messageCreateRequest = objectMapper.convertValue(request.get("messageCreateRequest"), MessageCreateRequest.class);

        // TypeReference는 Java의 Generic Type Erasure로 인해 런타임에 제네릭 타입 정보가 사라지는 문제를 해결
        // Type Erasure는 컴파일 후 제네릭 타입 정보가 바이트 코드에서 사라지는 Java의 특성
        Optional<List<BinaryContentCreateRequest>> optionalObjects = Optional.ofNullable(request.get("binaryContentCreateRequests"))
                .map(object -> objectMapper.convertValue(object, new TypeReference<List<BinaryContentCreateRequest>>() {}));
        List<BinaryContentCreateRequest> binaryContentCreateRequests = optionalObjects.orElse(new ArrayList<>());

        Message message = messageService.create(messageCreateRequest,  binaryContentCreateRequests);

        return ResponseEntity.ok(message);
    }

    @RequestMapping(value = "/{messageId}", method = RequestMethod.PUT)
    public ResponseEntity<Message> update(@PathVariable UUID messageId, @RequestBody MessageUpdateRequest request) {
        Message message =  messageService.update(messageId, request);

        return ResponseEntity.ok(message);
    }

    @RequestMapping(value = "/{messageId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable UUID messageId) {
        messageService.delete(messageId);

        return ResponseEntity.ok("Message with id " + messageId + " has been deleted");
    }

    @RequestMapping(value = "/{channelId}", method = RequestMethod.GET)
    public ResponseEntity<List<Message>> getMessages(@PathVariable UUID channelId) {
        List<Message> messages = messageService.findAllByChannelId(channelId);

        return ResponseEntity.ok(messages);
    }
}
