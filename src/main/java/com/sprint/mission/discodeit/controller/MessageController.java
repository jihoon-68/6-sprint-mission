package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.messagedto.CreateMessageRequest;
import com.sprint.mission.discodeit.dto.messagedto.UpdateMessageRequest;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

  private final MessageService messageService;

  @GetMapping
  public ResponseEntity<List<Message>> readMessageByChannelId(
      @RequestParam UUID channelId
  ) {
    List<Message> messageList = messageService.findAllByChannelId(channelId);
    return ResponseEntity.ok(messageList);
  }

  // todo 첨부파일 업로드 가능?
  @PostMapping
  public ResponseEntity<Message> createMessage(
      @RequestPart @Valid CreateMessageRequest request,
      @RequestPart List<MultipartFile> attachments
  ) {

    Message message = messageService.create(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(message);
  }

  @PatchMapping("/{messageId}")
  public ResponseEntity<Message> updateMessage(
      @PathVariable UUID messageId,
      @RequestBody @Valid UpdateMessageRequest request
  ) {
    Message message = messageService.update(messageId, request);
    return ResponseEntity.ok(message);
  }

  @DeleteMapping("/{messageId}")
  public ResponseEntity<String> deleteChannel(
      @PathVariable UUID messageId
  ) {
    messageService.delete(messageId);
    return ResponseEntity.ok(messageId + " 삭제되었습니다");
  }

}
