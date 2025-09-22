package com.sprint.mission.discodeit.channel.controller;

import com.sprint.mission.discodeit.channel.ChannelDto.Request.PublicRequest;
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
@RequestMapping("/public-channels")
public class PublicChannelController {

    private final ChannelService channelService;

    public PublicChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }

    @PostMapping
    public ResponseEntity<Response> createPublicChannel(@RequestBody @Valid PublicRequest request) {
        Response body = channelService.createChannel(request);
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Set<Response>> getPublicChannels() {
        Set<Response> body = channelService.getChannelsByChannelType(ChannelType.PUBLIC);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getPublicChannelById(@PathVariable UUID id) {
        Response body = channelService.getChannelByChannelTypeAndId(ChannelType.PUBLIC, id);
        return ResponseEntity.ok(body);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updatePublicChannelById(
            @PathVariable UUID id,
            @RequestBody @Valid PublicRequest request
    ) {
        Response body = channelService.updateChannelById(id, request);
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublicChannelById(@PathVariable UUID id) {
        channelService.deleteChannelById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
