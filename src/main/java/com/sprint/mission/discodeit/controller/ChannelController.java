package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.Channel.CreatePrivateChannelDTO;
import com.sprint.mission.discodeit.dto.Channel.CreatePublicChannelDTO;
import com.sprint.mission.discodeit.dto.Channel.FindChannelDTO;
import com.sprint.mission.discodeit.dto.Channel.UpdateChannelDTO;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@Tag(name = "Channel", description = "Channel API")
@RequestMapping("/api/channels")
@RestController
@RequiredArgsConstructor
public class ChannelController {
    private final ChannelService channelService;

    @Operation(summary = "Public Channel 생성")
    @ApiResponse(responseCode = "201", description = "Public Channel이 성공적으로 생성됨")
    @PostMapping("/public")
    public ResponseEntity<Channel> createPublic(@Schema(description = "Public Channel 생성 정보") @RequestBody CreatePublicChannelDTO createPublicChannelDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(channelService.createPublic(createPublicChannelDTO));
    }

    @Operation(summary = "Private Channel 생성")
    @ApiResponse(responseCode = "201", description = "Private Channel이 성공적으로 생성됨")
    @PostMapping("/private")
    public ResponseEntity<Channel> createPrivate(@Schema(description = "Private Channel 생성 정보") @RequestBody CreatePrivateChannelDTO createPrivateChannelDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(channelService.createPrivate(createPrivateChannelDTO));
    }

    @Operation(summary = "Channel 정보 수정")
    @ApiResponse(responseCode = "200", description = "Channel 정보가 성공적으로 수정됨")
    @ApiResponse(responseCode = "400", description = "Private Channel은 수정할 수 없음")
    @ApiResponse(responseCode = "404", description = "Channel을 찾을 수 없음")
    @PatchMapping("/{channelId}")
    public ResponseEntity<Channel> update(
            @Schema(description = "삭제할 Channel ID") @PathVariable UUID channelId,
            @Schema(description = "수정할 Channel ID") @RequestBody UpdateChannelDTO updateChannelDTO) {
        return ResponseEntity.status(200).body(channelService.update(channelId,updateChannelDTO));
    }

    @Operation(summary = "Channel 삭제")
    @ApiResponse(responseCode = "204", description = "Channel이 성공적으로 삭제됨")
    @ApiResponse(responseCode = "404", description = "Channel을 찾을 수 없음")
    @DeleteMapping("/{channelId}")
    public ResponseEntity<Void> delete(@Schema(description = "삭제할 Channel ID")@PathVariable UUID channelId) {
        channelService.delete(channelId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "User가 참여 중인 Channel 목록 조회")
    @ApiResponse(responseCode = "200", description = "Channel 목록 조회 성공")
    @GetMapping
    public ResponseEntity<List<FindChannelDTO>> findAll(@Schema(description = "조회할 User ID") @RequestParam("userId") UUID id) {
        List<FindChannelDTO> findChannelDTOS = channelService.findAllByUserId(id);
        return ResponseEntity.status(HttpStatus.OK).body(findChannelDTOS);
    }
}
