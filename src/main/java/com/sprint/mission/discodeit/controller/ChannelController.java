package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.DTO.Channel.CreatePrivateChannelDTO;
import com.sprint.mission.discodeit.DTO.Channel.CreatePublicChannelDTO;
import com.sprint.mission.discodeit.DTO.Channel.FindChannelDTO;
import com.sprint.mission.discodeit.DTO.Channel.UpdateChannelDTO;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/channel")
@RestController
@RequiredArgsConstructor
public class ChannelController {
    private final ChannelService channelService;

    @RequestMapping(value = "/public", method = RequestMethod.POST, consumes = "application/json")
    public Channel createPublic(@RequestBody CreatePublicChannelDTO createPublicChannelDTO) {
        return channelService.createPublic(createPublicChannelDTO);
    }

    @RequestMapping(value = "/private",method = RequestMethod.POST, consumes = "application/json")
    public Channel createPrivate(@RequestBody CreatePrivateChannelDTO createPrivateChannelDTO) {
        return channelService.createPrivate(createPrivateChannelDTO);
    }


    @RequestMapping(value = "",method = RequestMethod.POST, consumes = "application/json")
    public void update(@RequestBody UpdateChannelDTO updateChannelDTO) {
        channelService.update(updateChannelDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        channelService.delete(id);
    }

    @GetMapping("/{id}")
    public List<FindChannelDTO> findAll(@PathVariable UUID id) {
        return channelService.findAllByUserId(id);
    }
}
