package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.channeldto.CreatePrivateChannelRequest;
import com.sprint.mission.discodeit.dto.channeldto.CreatePublicChannelRequest;
import com.sprint.mission.discodeit.dto.channeldto.UpdateChannelRequest;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/channels")
@RequiredArgsConstructor
public class ChannelController {

  private final ChannelService channelService;

  @GetMapping
  public ResponseEntity<List<Channel>> readChannelByUserId(
      @RequestParam UUID userId) {
    List<Channel> channel = channelService.findAllByUserId(userId);
    return ResponseEntity.ok(channel);
  }

  //todo public,private 생성
  @PostMapping("/public")
  public ResponseEntity<Channel> createPublicChannel(
      @RequestBody CreatePublicChannelRequest request
  ) {
    Channel channel = channelService.createPublic(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(channel);
  }

  @PostMapping("/private")
  public ResponseEntity<Channel> createPrivateChannel(
      @RequestBody @Valid CreatePrivateChannelRequest request
  ) {
    Channel channel = channelService.createPrivate(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(channel);
  }

  @PatchMapping("/{channelId}")
  public ResponseEntity<Channel> updatePublicChannel(
      @PathVariable UUID channelId,
      @RequestBody @Valid UpdateChannelRequest request
  ) {
    Channel channel = channelService.update(channelId, request);
    return ResponseEntity.ok(channel);
  }

  @DeleteMapping("/{channelId}")
  public ResponseEntity<String> deleteChannel(
      @PathVariable UUID channelId
  ) {
    channelService.delete(channelId);
    return ResponseEntity.ok(channelId + " 삭제되었습니다");
  }
}