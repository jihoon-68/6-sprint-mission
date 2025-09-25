package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.DTO.Message.CreateMessageDTO;
import com.sprint.mission.discodeit.DTO.Message.FindMessageDTO;
import com.sprint.mission.discodeit.DTO.Message.MessageUpdateRequest;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Tag(name = "Message", description = "Message API")
@RequestMapping("/api/messages")
@RestController
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @Operation(summary = "Message 생성")
    @ApiResponse(responseCode = "201", description = "Public Channel이 성공적으로 생성됨")
    @ApiResponse(responseCode = "404", description = "Channel 또는 User를 찾을 수 없음")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Message> createMessage(
            @Schema(description = "Message 생성 정보")
            @RequestPart(name = "messageCreateRequest") CreateMessageDTO messageCreateRequest,
            @Schema(description = "Message 첨부 파일들")
            @RequestPart(name = "attachments", required = false) List<MultipartFile> attachments) {
        return ResponseEntity.status(HttpStatus.CREATED).body(messageService.create(attachments, messageCreateRequest));
    }

    @Operation(summary = "Message 내용 수정")
    @ApiResponse(responseCode = "200", description = "Message가 성공적으로 생성됨")
    @ApiResponse(responseCode = "404", description = "Channel 또는 User를 찾을 수 없음")
    @PatchMapping(value = "/{messageId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Message> updateMessage(
            @Schema(description = "수정할 Message ID")
            @PathVariable UUID messageId,
            @Schema(description = "수정할 Message 내용")
            @RequestBody MessageUpdateRequest newContent) {
        return ResponseEntity.status(HttpStatus.OK).body(messageService.update(messageId, newContent.newContent()));
    }

    @Operation(summary = "Message 삭제")
    @ApiResponse(responseCode = "204", description = "Message가 성공적으로 삭제됨")
    @ApiResponse(responseCode = "404", description = "Message를 찾을 수 없음")
    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> deleteMessage(
            @Schema(description = "삭제할 Message ID")
            @PathVariable UUID messageId) {
        messageService.delete(messageId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Channel의 Message 목록 조회")
    @ApiResponse(responseCode = "200", description = "Message 목록 조회 성공")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Message>> getMessage(
            @Schema(description = "Message가 성공적으로 수정됨")
            @RequestParam("channelId") UUID id) {
        List<Message> messageList = messageService.findAllByChannelId(id);
        return ResponseEntity.status(HttpStatus.OK).body(messageList);
    }
}
