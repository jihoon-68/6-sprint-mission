package com.sprint.mission.discodeit.channel.controller;

import com.sprint.mission.discodeit.channel.ChannelDto.Request;
import com.sprint.mission.discodeit.channel.ChannelDto.Request.PublicRequest;
import com.sprint.mission.discodeit.channel.ChannelDto.Response;
import com.sprint.mission.discodeit.channel.service.ChannelService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/channels")
public class ChannelController {

    private final ChannelService channelService;

    public ChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }

    @PostMapping
    public ResponseEntity<Response> createChannel(@RequestBody @Valid Request request) {
        Response body = channelService.createChannel(request);
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Set<Response>> getChannels(@RequestParam String type) {
        Set<Response> body = channelService.getChannelsByChannelType(type);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getChannelById(@PathVariable UUID id, @RequestParam String type) {
        Response body = channelService.getChannelByChannelTypeAndId(type, id);
        return ResponseEntity.ok(body);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateChannelById(
            @PathVariable UUID id,
            @RequestBody @Valid PublicRequest request
    ) {
        Response body = channelService.updateChannelById(id, request);
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChannelById(@PathVariable UUID id) {
        channelService.deleteChannelById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
