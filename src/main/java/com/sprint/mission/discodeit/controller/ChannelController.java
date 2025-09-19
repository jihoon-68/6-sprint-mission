package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.data.ChannelDto;
import com.sprint.mission.discodeit.dto.request.PrivateChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelUpdateRequest;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/channel")
public class ChannelController {
    private final ChannelService channelService;

    public ChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }

    @PostMapping("/createPublic")
    public Channel createPublic(@RequestBody PublicChannelCreateRequest request){
        return channelService.create(request);
    }

    @PostMapping("/createPrivate")
    public Channel createPrivate(@RequestBody PrivateChannelCreateRequest request){
        return channelService.create(request);
    }

    @PutMapping("/updatePublic")
    public Channel updatePublic(@RequestBody PublicChannelUpdateRequest request,
                                @RequestParam UUID channelId){
        return channelService.update(channelId, request);
    }

    @DeleteMapping("/delete")
    public void deletePublic(@RequestParam UUID channelId){
        channelService.delete(channelId);
    }

    @GetMapping("/findAllByUserId")
    public List<ChannelDto> findAllByUserId(@RequestParam("userId") UUID userId){
        return channelService.findAllByUserId(userId);
    }
}
