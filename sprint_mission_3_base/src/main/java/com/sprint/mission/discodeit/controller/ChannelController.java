package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.data.ChannelDto;
import com.sprint.mission.discodeit.dto.request.PrivateChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelUpdateRequest;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/channels")
public class ChannelController {
    private final ChannelService channelService;

    public ChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }

    @PostMapping("/createPublicChannel")
    public Channel createPublicChannel(@RequestBody PublicChannelCreateRequest request) {
        return channelService.create(request);
    }

    @PostMapping("/createPrivateChannel")
    public Channel createPrivateChannel(@RequestBody PrivateChannelCreateRequest request) {
        return channelService.create(request);
    }

    @PostMapping("/updateChannel")
    public Channel UpdateChannel(@RequestParam UUID channelId, @RequestBody PublicChannelUpdateRequest request) {
        return channelService.update(channelId, request);
    }

    @DeleteMapping("/deleteChannel")
    public void deleteChannel(@RequestParam UUID channelId) {
        channelService.delete(channelId);
    }

    @GetMapping("/getAllChannels")
    public List<ChannelDto> getAllChannelsByUserId(@RequestParam UUID userId) {
        return channelService.findAllByUserId(userId);
    }
}
