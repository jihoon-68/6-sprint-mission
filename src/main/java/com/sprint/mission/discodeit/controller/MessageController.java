package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.request.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageUpdateRequest;
import com.sprint.mission.discodeit.dto.request.MessageCreateRequest.CreateMessageWithContent;
import com.sprint.mission.discodeit.dto.response.MessageResponse;
import com.sprint.mission.discodeit.dto.response.PagedResponse;
import com.sprint.mission.discodeit.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

@Tag(name = "Message")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @Operation(summary = "Message 생성", operationId = "createMessage")
    public ResponseEntity<MessageResponse> create(
            @RequestPart("request") MessageCreateRequest request
    ) throws IOException {
        CreateMessageWithContent createRequest = request.toContentDto();
        MessageResponse createdMessage = messageService.create(createRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdMessage);
    }

    @Operation(summary = "특정 Message 조회", operationId = "findMessageById")
    @GetMapping(path = "/{messageId}")
    public ResponseEntity<MessageResponse> find(@PathVariable("messageId") UUID messageId) {
        MessageResponse message = messageService.find(messageId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(message);
    }

    @Operation(summary = "Message 수정", operationId = "updateMessage")
    @PutMapping(path = "/{messageId}")
    public ResponseEntity<MessageResponse> update(@PathVariable("messageId") UUID messageId,
                                                  @RequestBody MessageUpdateRequest request) {
        MessageResponse updatedMessage = messageService.update(messageId, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedMessage);
    }

    @Operation(summary = "Message 삭제", operationId = "deleteMessage")
    @DeleteMapping(path = "/{messageId}")
    public ResponseEntity<Void> delete(@PathVariable("messageId") UUID messageId) {
        messageService.delete(messageId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Operation(summary = "채널별 Message 목록 조회 (페이지네이션)", operationId = "findAllMessageByChannelId")
    @GetMapping(path = "/channel/{channelId}")
    public ResponseEntity<PagedResponse<MessageResponse>> findAllByChannelId(
            @PathVariable("channelId") UUID channelId,
            @RequestParam(value = "page", defaultValue = "0") int page
    ) {

        PagedResponse<MessageResponse> messages = messageService.findAllByChannelId(channelId, page);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(messages);
    }
}