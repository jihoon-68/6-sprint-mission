package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.data.ChannelDto;
import com.sprint.mission.discodeit.dto.request.PrivateChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelUpdateRequest;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.UUID;
import java.util.List;

@Controller
@RequestMapping("/channel")
public class ChannelController {

    @Autowired
    private ChannelService channelService;

    public ChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }

    @RequestMapping(path = "/public", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Channel> createPublicChannel(@RequestBody PublicChannelCreateRequest channel) {

        var newchannel = channelService.create(channel);

        return ResponseEntity.status(HttpStatus.CREATED).body(newchannel);
    }

    @RequestMapping(path = "/private", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Channel> createPrivateChannel(@RequestBody PrivateChannelCreateRequest channel) {

        var newchannel = channelService.create(channel);

        return ResponseEntity.status(HttpStatus.CREATED).body(newchannel);
    }

    @RequestMapping(path = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Channel> updateChannel(@PathVariable UUID id, @RequestBody PublicChannelUpdateRequest channel) {

        var updated = channelService.update(id, channel);

        return ResponseEntity.status(HttpStatus.CREATED).body(updated);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Channel> deleteChannel(@PathVariable UUID id) {
        channelService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @RequestMapping(path = "accessible/{uuid}", method = RequestMethod.GET)
    public ResponseEntity<List<ChannelDto>> accessibleChannels(@PathVariable UUID uuid) {
        var channels = channelService.findAllByUserId(uuid);

        return ResponseEntity.status(HttpStatus.OK).body(channels);
    }
}
