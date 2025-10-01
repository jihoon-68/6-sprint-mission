package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.channel.ChannelResponseDto;
import com.sprint.mission.discodeit.dto.channel.PublicChannelUpdateRequestDto;
import com.sprint.mission.discodeit.dto.channel.PrivateChannelCreateRequestDto;
import com.sprint.mission.discodeit.dto.channel.PublicChannelCreateRequestDto;
import com.sprint.mission.discodeit.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/channels")
@RequiredArgsConstructor
public class ChannelController {

    private final ChannelService channelService;

    @PostMapping("/private")
    public ResponseEntity<ChannelResponseDto> createPrivateChannel(@RequestBody PrivateChannelCreateRequestDto dto) {
        ChannelResponseDto channel = channelService.createPrivateChannel(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED) // 201 Created
                .body(channel);
    }

    @PostMapping("/public")
    public ResponseEntity<ChannelResponseDto> createPublicChannel(@RequestBody PublicChannelCreateRequestDto dto) {
        ChannelResponseDto channel = channelService.createPublicChannel(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED) // 201 Created
                .body(channel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ChannelResponseDto>> findAll(@PathVariable UUID id) {
        return ResponseEntity.ok(channelService.findAllByUserId(id));
    }


    @PatchMapping("/{id}")
    public ResponseEntity<ChannelResponseDto> update(@PathVariable UUID id,
                                                     @RequestBody PublicChannelUpdateRequestDto dto) { // TODO dto에서 id 분리
        ChannelResponseDto channel = channelService.update(id, dto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(channel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        channelService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
