package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.channel.ChannelResponseDto;
import com.sprint.mission.discodeit.dto.channel.ChannelUpdateRequestDto;
import com.sprint.mission.discodeit.dto.channel.PrivateChannelCreateRequestDto;
import com.sprint.mission.discodeit.dto.channel.PublicChannelCreateRequestDto;
import com.sprint.mission.discodeit.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/channels")
@RequiredArgsConstructor
public class ChannelController {

    private final ChannelService channelService;

    @PostMapping("/create-private/")
    public ResponseEntity<ChannelResponseDto> createPrivateChannel(@RequestBody PrivateChannelCreateRequestDto dto) {
        return ResponseEntity.ok(channelService.createPrivateChannel(dto));
    }

    @PostMapping("/create-public/")
    public ResponseEntity<ChannelResponseDto> createPublicChannel(@RequestBody PublicChannelCreateRequestDto dto) {
        return ResponseEntity.ok(channelService.createPublicChannel(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ChannelResponseDto>> findAllByUserId(@PathVariable UUID id) {
        return ResponseEntity.ok(channelService.findAllByUserId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChannelResponseDto> update(@PathVariable UUID id,
                                                     @RequestBody ChannelUpdateRequestDto dto) { // TODO dto에서 id 분리
        return ResponseEntity.ok(channelService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        channelService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
