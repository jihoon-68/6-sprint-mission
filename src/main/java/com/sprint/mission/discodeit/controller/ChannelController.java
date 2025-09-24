package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.data.ChannelDto;
import com.sprint.mission.discodeit.dto.request.PrivateChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelUpdateRequest;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/channel")
public class ChannelController {
    private final ChannelService channelService;

    @RequestMapping(value = "public", method = RequestMethod.POST)
    public ResponseEntity<Channel> addPublicChannel(@RequestBody PublicChannelCreateRequest request) {
        Channel channel = channelService.create(request);

        return ResponseEntity.ok(channel);
    }

    @RequestMapping(value = "private", method = RequestMethod.POST)
    public ResponseEntity<Channel> addPrivateChannel(@RequestBody PrivateChannelCreateRequest request) {
        Channel channel = channelService.create(request);
        return ResponseEntity.ok(channel);
    }

    @RequestMapping(value = "/{channelId}", method = RequestMethod.PUT)
    public ResponseEntity<Channel> updateChannel(@PathVariable UUID channelId, @RequestBody PublicChannelUpdateRequest request) {
        Channel channel = channelService.update(channelId, request);

        return ResponseEntity.ok(channel);
    }

    @RequestMapping(value = "/{channelId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteChannel(@PathVariable UUID channelId) {
        channelService.delete(channelId);
        return ResponseEntity.ok("Channel with id " + channelId + " was deleted");
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<List<ChannelDto>> getChannel(@PathVariable UUID userId) {
        List<ChannelDto> channels = channelService.findAllByUserId(userId);

        return ResponseEntity.ok(channels);
    }
}
