package com.sprint.mission.discodeit.channel.controller;

import com.sprint.mission.discodeit.channel.ChannelDto.Request.PrivateRequest;
import com.sprint.mission.discodeit.channel.ChannelDto.Response;
import com.sprint.mission.discodeit.channel.domain.Channel.ChannelType;
import com.sprint.mission.discodeit.channel.service.ChannelService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/private-channels")
public class PrivateChannelController {

    private final ChannelService channelService;

    public PrivateChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }

    @PostMapping
    public ResponseEntity<Response> createPrivateChannel(@RequestBody @Valid PrivateRequest request) {
        Response body = channelService.createChannel(request);
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Set<Response>> getPrivateChannels() {
        Set<Response> body = channelService.getChannelsByChannelType(ChannelType.PRIVATE);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getPrivateChannelById(@PathVariable UUID id) {
        Response body = channelService.getChannelByChannelTypeAndId(ChannelType.PRIVATE, id);
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrivateChannelById(@PathVariable UUID id) {
        channelService.deleteChannelById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
