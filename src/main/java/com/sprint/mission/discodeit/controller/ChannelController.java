package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.channel.ChannelResponse;
import com.sprint.mission.discodeit.dto.channel.CreatePrivateChannelRequest;
import com.sprint.mission.discodeit.dto.channel.CreatePublicChannelRequest;
import com.sprint.mission.discodeit.dto.channel.UpdateChannelRequest;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/channels")
@RequiredArgsConstructor
public class ChannelController {

  private final ChannelService channelService;

  @GetMapping
  public ResponseEntity<List<ChannelResponse>> readChannelByUserId(
      @RequestParam UUID userId) {
    List<ChannelResponse> responses = channelService.findAllByUserId(userId);
    return ResponseEntity.ok(responses);
  }

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