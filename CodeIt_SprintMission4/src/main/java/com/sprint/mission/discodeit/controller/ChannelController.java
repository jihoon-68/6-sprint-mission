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
@RequestMapping("/channels")
public class ChannelController {
    private final ChannelService channelService;

    public ChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<ChannelDto> getChannels(@RequestParam UUID userId) {
        return channelService.findAllByUserId(userId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/public")
    public Channel createPublicChannel(@RequestBody PublicChannelCreateRequest request) {
        return channelService.create(request);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/private")
    public Channel createPrivateChannel(@RequestBody PrivateChannelCreateRequest request) {
        return channelService.create(request);
    }

    @RequestMapping(value = "/{channelId}/public", method = RequestMethod.PATCH)
    public Channel updatePublicChannel(@PathVariable UUID channelId, @RequestBody PublicChannelUpdateRequest request) {
        return channelService.update(channelId, request);
    }

    @RequestMapping(value = "/{channelId}", method = RequestMethod.DELETE)
    public void deleteChannel(@PathVariable UUID channelId) {
        channelService.delete(channelId);
    }
}
