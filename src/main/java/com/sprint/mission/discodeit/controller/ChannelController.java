package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.channeldto.CreateChannelDto;
import com.sprint.mission.discodeit.dto.channeldto.UpdateChannelDto;
import com.sprint.mission.discodeit.dto.userdto.CreateUserDto;
import com.sprint.mission.discodeit.dto.userdto.UpdateUserDto;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/channels")
@RequiredArgsConstructor
public class ChannelController {

    private final ChannelService channelService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<Channel>> readChannel(
            @PathVariable UUID userId) {
        List<Channel> channel = channelService.findAllByUserId(userId);
        return ResponseEntity.ok(channel);
    }

    @PostMapping
    public ResponseEntity<Channel> createChannel(
            @RequestBody CreateChannelDto createChannelDto
            ) {
        Channel channel = channelService.create(createChannelDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(channel);
    }

    @PutMapping("/{channelId}")
    public ResponseEntity<Channel> updatePublicChannel(
            @PathVariable UUID channelId,
            @RequestBody UpdateChannelDto updateChannelDto
            ) {
        Channel channel = channelService.update(channelId,updateChannelDto);
        return ResponseEntity.ok(channel);
    }

    @DeleteMapping("/{channelId}")
    public ResponseEntity<String> deleteChannel(
            @PathVariable UUID channelId
    ){
        channelService.delete(channelId);
        return ResponseEntity.ok(channelId +" 삭제되었습니다");
    }
}